package com.vlsm;

public class ipv4Address {
    private int[] ip;
    private int[] subnetMask;

    public ipv4Address(int[] ip, int[] subnetMask) {
        this.ip = ip;
        this.subnetMask = subnetMask;
    }

    public ipv4Address(String ip, String subnetMask) {
        String[] splittedIp = ip.split("\\.");
        int[] intSplittedIp = { Integer.parseInt(splittedIp[0]), Integer.parseInt(splittedIp[1]),
                Integer.parseInt(splittedIp[2]), Integer.parseInt(splittedIp[3]) };
        this.ip = intSplittedIp;

        if (subnetMask.contains(".")) {
            String[] splittedSm = subnetMask.split("\\.");
            int[] intSplittedSm = { Integer.parseInt(splittedSm[0]), Integer.parseInt(splittedSm[1]),
                    Integer.parseInt(splittedSm[2]), Integer.parseInt(splittedSm[3]) };
            this.subnetMask = intSplittedSm;
        } else {
            int cidr = Integer.parseInt(subnetMask);
            String bits = "";
            int count = 0;
            while (cidr > 0) {
                while (count != 8 && cidr > 0) {
                    bits += "1";
                    cidr--;
                    count++;
                }
                count = 0;
                if (cidr != 0)
                    bits += ".";
            }
            // System.out.println(bits);
            count = bits.split("\\.")[bits.split("\\.").length - 1].length();
            int nBits = bits.replaceAll("\\.", "").length();
            while (nBits < 32) {
                while (count != 8) {
                    bits += "0";
                    nBits++;
                    count++;
                }
                count = 0;
                if (nBits != 32)
                    bits += ".";
            }
            String[] splittedSm = bits.split("\\.");
            // System.out.println(bits);
            int[] intSplittedSm = { Integer.parseInt(splittedSm[0], 2), Integer.parseInt(splittedSm[1], 2),
                    Integer.parseInt(splittedSm[2], 2), Integer.parseInt(splittedSm[3], 2) };
            this.subnetMask = intSplittedSm;
        }
    }

    public int[] getIp() {
        return ip.clone();
    }

    public void setIp(int[] ip) {
        this.ip = ip;
    }

    public int[] getSubnetMask() {
        return subnetMask.clone();
    }

    public void setSubnetMask(int[] subnetMask) {
        this.subnetMask = subnetMask;
    }

    public void setSubnetMask(int subnetMask) {
        int cidr = subnetMask;
        String bits = "";
        int count = 0;
        while (cidr > 0) {
            while (count != 8 && cidr > 0) {
                bits += "1";
                cidr--;
                count++;
            }
            count = 0;
            if (cidr != 0)
                bits += ".";
        }
        // System.out.println(bits);
        count = bits.split("\\.")[bits.split("\\.").length - 1].length();
        int nBits = bits.replaceAll("\\.", "").length();
        while (nBits < 32) {
            while (count != 8) {
                bits += "0";
                nBits++;
                count++;
            }
            count = 0;
            if (nBits != 32)
                bits += ".";
        }
        String[] splittedSm = bits.split("\\.");
        // System.out.println(bits);
        int[] intSplittedSm = { Integer.parseInt(splittedSm[0], 2), Integer.parseInt(splittedSm[1], 2),
                Integer.parseInt(splittedSm[2], 2), Integer.parseInt(splittedSm[3], 2) };
        this.subnetMask = intSplittedSm;
    }

    public void setSubnetMask(String subnetMask) {
        if (subnetMask.contains(".")) {
            String[] splittedSm = subnetMask.split("\\.");
            int[] intSplittedSm = { Integer.parseInt(splittedSm[0]), Integer.parseInt(splittedSm[1]),
                    Integer.parseInt(splittedSm[2]), Integer.parseInt(splittedSm[3]) };
            this.subnetMask = intSplittedSm;
        } else {
            int cidr = Integer.parseInt(subnetMask);
            String bits = "";
            int count = 0;
            while (cidr > 0) {
                while (count != 8 && cidr > 0) {
                    bits += "1";
                    cidr--;
                    count++;
                }
                count = 0;
                if (cidr != 0)
                    bits += ".";
            }
            // System.out.println(bits);
            count = bits.split("\\.")[bits.split("\\.").length - 1].length();
            int nBits = bits.replaceAll("\\.", "").length();
            while (nBits < 32) {
                while (count != 8) {
                    bits += "0";
                    nBits++;
                    count++;
                }
                count = 0;
                if (nBits != 32)
                    bits += ".";
            }
            String[] splittedSm = bits.split("\\.");
            // System.out.println(bits);
            int[] intSplittedSm = { Integer.parseInt(splittedSm[0], 2), Integer.parseInt(splittedSm[1], 2),
                    Integer.parseInt(splittedSm[2], 2), Integer.parseInt(splittedSm[3], 2) };
            this.subnetMask = intSplittedSm;
        }
    }

    public int getCidr() {
        String binarySubnetMask = "";
        for (int i : subnetMask) {
            binarySubnetMask += Integer.toBinaryString(i);
        }
        int cidr = binarySubnetMask.lastIndexOf("1") + 1;
        return cidr;
    }

    public String toString() {
        return this.ip[0] + "." + this.ip[1] + "." + this.ip[2] + "." + this.ip[3] + " /" + this.getCidr();
    }
}
