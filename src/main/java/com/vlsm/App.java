package com.vlsm;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        //user input
        /*
         * System.out.println("-Enter ip address: ");
         * Scanner scanner = new Scanner(System.in);
         * String ip = scanner.next();
         * System.out.println("-Enter subnet mask: ");
         * String sm = scanner.next();
         * scanner.close();
         */

        ipv4Address ipv4a = new ipv4Address("192.168.4.0", "255.255.255.0");
        System.out.println(ipv4a.toString());

        //user input
        /*
         * System.out.println("-Enter the number of LANs: ");
         * Scanner scanner = new Scanner(System.in);
         * int nLan = scanner.nextInt();
         * int[] hosts = new int[nLan];
         * for (int i = 0; i < nLan; i++) {
         * System.out.println("-Hosts (network and broadcast IPs excluded) for LAN " +
         * (i+1) + ":");
         * hosts[i] = scanner.nextInt()+2;
         * }
         * scanner.close();
         */

        //mockup
        int nLan = 6;
        int[] hosts = { 29 + 2, 60 + 2, 2 + 2, 2 + 2, 2 + 2, 14 + 2 };

        // orders hosts from max to min value
        int max;
        int temp;
        int shift = 0;
        while (shift <= hosts.length - 2) {
            max = shift;
            for (int i = shift; i < hosts.length-1; i++) {
                if(hosts[i+1] > hosts[max])
                    max = i+1;
            }
            temp = hosts[shift];
            hosts[shift] = hosts[max];
            hosts[max] = temp;
            shift++;
        }
        /*for (int i : hosts) {
            System.out.println(i);
        }*/

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
            System.out.println("hostId: " + hostId);

            int netId = 32 - hostId;

        }
    }
}
