package com.frog.core;

import java.io.UnsupportedEncodingException;

/**
 * classname: RedisUtil
 * description: RedisUtil
 * date: 2019/4/20 17:22
 *
 * @auther lu
 */
public class RedisUtil {

    public static byte[] int2charBytes(int i) {
        try {
            return (i + "").getBytes(Stipulation.ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RedisException("GetBytes Error: " + e);
        }
    }

    public static byte[] encoding(String str) {
        try {
            return str.getBytes(Stipulation.ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RedisException("Encoding Error: " + e);
        }
    }

    public static byte[][] encoding(String... strs) {
        byte[][] bytes = new byte[strs.length][];
        for (int i = 0; i < strs.length; i++) {
            bytes[i] = encoding(strs[i]);
        }
        return bytes;
    }

    public static String decoding(byte[] bytes) {
        try {
            return new String(bytes, Stipulation.ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RedisException("Decoding Error: " + e);
        }
    }

    public static byte[] ctr() {
        byte[] rn = new byte[2];
        rn[0] = '\r';
        rn[1] = '\n';
        return rn;
    }
}
