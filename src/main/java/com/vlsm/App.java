package com.vlsm;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        // user input for ip and sm
        System.out.println("-Enter ip address: ");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.next();
        System.out.println("-Enter subnet mask: ");
        String sm = scanner.next();

        System.out.println("==================================");

        // user input for number of LANs and number of HOSTs for each LAN
        System.out.println("-Enter the number of LANs: ");
        int nLan = scanner.nextInt();
        int[] hosts = new int[nLan];
        for (int i = 0; i < nLan; i++) {
            System.out.println("-Hosts (network and broadcast IPs excluded) for LAN " +
            (i + 1) + ":");
            hosts[i] = scanner.nextInt() + 2;
        }
        scanner.close();
        
        ipv4Address starterAddress = new ipv4Address(ip, sm);
        System.out.println("==================================\n-Starter address: " + starterAddress.toString());

        // orders hosts number array from max to min value
        int max;
        int temp;
        int shift = 0;
        while (shift <= hosts.length - 2) {
            max = shift;
            for (int i = shift; i < hosts.length - 1; i++) {
                if (hosts[i + 1] > hosts[max])
                    max = i + 1;
            }
            temp = hosts[shift];
            hosts[shift] = hosts[max];
            hosts[max] = temp;
            shift++;
        }
        /*
         * for (int i : hosts) {
         * System.out.println(i);
         * }
         */

        // calculates the subnets
        ArrayList<ipv4Address> subnets = new ArrayList<ipv4Address>();
        ipv4Address unassignedSubnet = new ipv4Address(starterAddress.getIp(), starterAddress.getSubnetMask());
        boolean found;
        for (int i : hosts) {
            found = false;
            // finds the host id
            int hostId = 0;
            while (!found) {
                if (Math.pow(2, hostId) >= i)
                    found = true;
                else
                    hostId++;
            }

            int netId = 32 - hostId;
            // System.out.println("hostId: " + hostId);
            // System.out.println("netId: " + netId);

            // adds subnet to array
            unassignedSubnet.setSubnetMask(netId);
            subnets.add(subnets.size(), new ipv4Address(unassignedSubnet.getIp(), unassignedSubnet.getSubnetMask()));

            // calculates ip's sector to update, -1 because index starts from 0
            int sector = (netId / 8) - 1;
            if (netId % 8 != 0)
                sector++;

            // updates unassigned ip
            int[] newIp = unassignedSubnet.getIp();
            newIp[sector] += Math.pow(2, 8 - (netId % 8));
            unassignedSubnet.setIp(newIp);
        }

        // prints the subnets
        for (ipv4Address subnet : subnets) {
            System.out.println(subnet.toString());
        }
    }
}
