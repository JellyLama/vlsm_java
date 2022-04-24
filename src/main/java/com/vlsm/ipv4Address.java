package com.vlsm;

public class ipv4Address {
    private final int[] address;
    private final int[] subnetMask;

    public ipv4Address(int[] address, int[] subnetMask) {
        this.address = address;
        this.subnetMask = subnetMask;
    }

    public int getCidr(){
        String binarySubnetMask = "";
        for (int i : subnetMask) {
            binarySubnetMask += Integer.toBinaryString(i);
        }
        int cidr = binarySubnetMask.lastIndexOf("1") + 1;
        return cidr;
    }

    public String toString(){
        return this.address[0] + this.address[1] + this.address[2] + this.address[3] + " /" + this.getCidr();
    }
}
