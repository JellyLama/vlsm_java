package com.vlsm;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /*System.out.println("-Enter ip address: ");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.next();
        System.out.println("-Enter subnet mask: ");
        String sm = scanner.next();
        scanner.close();*/
        
        ipv4Address ipv4a = new ipv4Address("192.168.4.0", "255.255.255.0");
        System.out.println(ipv4a.toString());

        System.out.println("-Enter the number of LANs: ");
        Scanner scanner = new Scanner(System.in);
        int nLan = scanner.nextInt();
        int[] hosts = new int[nLan];
        for (int i = 0; i < nLan; i++) {
            System.out.println("-Hosts for LAN " + (i+1) + ":");
            hosts[i] = scanner.nextInt();
        }
        scanner.close();
    }
}
