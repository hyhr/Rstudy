package com.r.study.tcp.gateway.tcp.utils;
/**
 * 字节工具类
 */
public class ByteUtils {

    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
            }
            stringBuilder.append(hv);
            stringBuilder.append(0);
        }
        return stringBuilder.toString();
    }
}
