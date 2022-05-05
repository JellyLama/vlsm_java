package com.vlsm;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws CloneNotSupportedException {

        // user input for ip and sm
        System.out.println("===================================================");
        Scanner scanner = new Scanner(System.in);
        String starterIp = "";
        String starterSm = "";

        boolean valid = false;
        while (!valid) {
            System.out.println("-Enter ip address: ");
            starterIp = scanner.next();
            valid = ipv4Address.validateIpv4(starterIp);

            if (!valid)
                System.out.println("\nINVALID IP ADDRESS!\n");
        }
        valid = false;
        while (!valid) {
            System.out.println("-Enter subnet mask: ");
            starterSm = scanner.next();
            valid = ipv4Address.validateSubnetMask(starterSm);

            if (!valid)
                System.out.println("\nINVALID SUBNETMASK!\n");
        }

        // user input for number of LANs and number of HOSTs for each LAN
        System.out.println("-Enter the number of LANs: ");
        int nLan = scanner.nextInt();
        int[] hosts = new int[nLan];
        int[] originalOrder = new int[nLan];

        for (int i = 0; i < nLan; i++) {
            originalOrder[i] = i;
            System.out.println("-Hosts (network and broadcast IPs excluded) for LAN " +
                    (i + 1) + ":");
            hosts[i] = scanner.nextInt() + 2;
        }

        // user input to exclude or not the first network IP
        char excludefirstIp = '0';
        while (excludefirstIp != 'y' && excludefirstIp != 'n') {
            System.out.println("-Do you want to exclude the first network IP [y/n]? ");
            excludefirstIp = scanner.next().charAt(0);
        }
        scanner.close();

        ipv4Address starterAddress = new ipv4Address(starterIp, starterSm);
        System.out.println("=================-Starter address-=================\n" + starterAddress.toString()
                + "\n=====================-Subnets-=====================");

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

            // saves the new LAN ID order due to the hosts sort
            temp = originalOrder[shift];
            originalOrder[shift] = originalOrder[max];
            originalOrder[max] = temp;

            shift++;
        }

        // calculates the subnets
        ArrayList<ipv4Address> subnets = new ArrayList<ipv4Address>();
        ipv4Address unassignedSubnet = new ipv4Address(starterAddress.getNetworkId(), starterAddress.getSubnetMask());
        for (int i : hosts) {
            boolean found = false;
            // finds the host id
            int hostId = 0;
            while (!found) {
                if (Math.pow(2, hostId) >= i)
                    found = true;
                else
                    hostId++;
            }

            int netId = 32 - hostId;
            if(netId < starterAddress.getCidr()){
                valid = false;
                System.out.println("\nNOT ENOUGH SPACE FOR " + i + " HOSTS\n");
                break;
            }
            unassignedSubnet.setSubnetMask("" + netId);

            String[] splittedNewIp = unassignedSubnet.getNetworkId().split("\\.");
            String[] splittedSm = unassignedSubnet.getSubnetMask().split("\\.");

            if (excludefirstIp == 'n')
                // adds subnet to array
                subnets.add(subnets.size(), (ipv4Address) unassignedSubnet.clone());

            // calculates ip's sector to update, -1 because index starts from 0
            int sector = (netId / 8) - 1;
            if (netId % 8 != 0)
                sector++;

            int newSectorValue = ((int) Math.pow(2, hostId % 8)) + Integer.parseInt(splittedNewIp[sector]);

            // if the value of the updated sector exceeds the max value (defined by the
            // subnet mask) then sector--
            while (newSectorValue > Integer.parseInt(splittedSm[sector])) {
                splittedNewIp[sector] = "0";
                sector--;
                newSectorValue = Integer.parseInt(splittedNewIp[sector]) + 1;
            }
            splittedNewIp[sector] = "" + newSectorValue;

            // updates unassigned ip (network id for the next subnet)
            unassignedSubnet.setIp(String.join(".", splittedNewIp));

            if (excludefirstIp == 'y')
                // adds subnet to array
                subnets.add(subnets.size(), (ipv4Address) unassignedSubnet.clone());
        }

        // prints the subnets
        if(valid){
            for (int i = 0; i < nLan; i++) {
                System.out.println("[LAN " + (originalOrder[i] + 1) + "]\n" + subnets.get(i).toString() + "\n");
            }
        }
    }
}
