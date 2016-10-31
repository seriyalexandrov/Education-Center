package com.aleksandrov.example.junit.mutation;

import java.util.HashMap;
import java.util.Map;

public class ArpTable {

    static final String DEFAULT_MAC_ADDRESS = "01:23:45:67:89:ab";
    final Map<String, String> macAddresses = new HashMap<>();

    public String getMacAddress(String ipAddress) {

        String mac = macAddresses.get(ipAddress);
        if (mac == null) {
            return DEFAULT_MAC_ADDRESS;
        } else {
            return mac;
        }
    }

    public void addIpMacPair(String ipAddress, String macAddress) {
        macAddresses.put(ipAddress, macAddress);
    }
}
