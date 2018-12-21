package com.bl.qa.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NetworkUtils {

    public static Map getCurrentMachineInfo() {
        final Map data = new HashMap();
        try {
            data.put("ip", InetAddress.getLocalHost());
            data.put("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return data;
    }
}
