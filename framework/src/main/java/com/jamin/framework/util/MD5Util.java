package com.jamin.framework.util;

import java.security.MessageDigest;

/**
 * Created by Jamin on 2016/4/20.
 */
public class MD5Util {

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    public final static String getMd5ByString(String str) {
        try {
            return getMd5ByByte(str.getBytes("UTF-8"));
        } catch (Exception e) {
            return null;
        }

    }

    public final static String getMd5ByByte(byte[] data) {
        if (data == null)
            return null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(data);
            return toHexString(messageDigest.digest());
        } catch (Exception e) {
            return null;
        }
    }

    private final static String toHexString(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }


    private final static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }


    private final static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
