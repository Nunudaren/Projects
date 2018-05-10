package cn.caijiajia.credittools.utils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Author:chendongdong
 * @Date:2018/4/24
 */
public class MD5Utils {
    private static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static MessageDigest messagedigest = null;

    public MD5Utils() {
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
        messagedigest.update(byteBuffer);
        in.close();
        return bufferToHex(messagedigest.digest());
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        MessageDigest var1 = messagedigest;
        synchronized (messagedigest) {
            messagedigest.update(bytes);
            return bufferToHex(messagedigest.digest());
        }
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for (int l = m; l < k; ++l) {
            appendHexPair(bytes[l], stringbuffer);
        }

        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    public static Object byteToObject(byte[] bytes) {
        Object obj = null;

        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return obj;
    }

    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;

        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            try {
                bo.close();
                oo.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

        return bytes;
    }

    public static String fileHash(byte[] data) {
        String result = null;

        for (int i = 0; i < data.length; ++i) {
            result = result + Integer.toString((data[i] & 255) + 256, 16).substring(1);
        }

        return result;
    }

    public static String getUtf8MD5String(String s) {
        try {
            byte[] bytes = s.getBytes("utf-8");
            return getMD5String(bytes);
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException(var2);
        }
    }

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            var1.printStackTrace();
        }

    }


    public static void main(String[] args) {
        Map<String, String> signMap = new HashMap<>();
        signMap.put("partner", "1");
        signMap.put("_input_charset", "UTF-8");
        signMap.put("time_key", System.currentTimeMillis() + "");
        signMap.put("notify_id", "notify_id");
        signMap.put("user_id", "user_id");
        signMap.put("mobileNo", "110");
        signMap.put("target_url", "www.google.com");
        signMap.put("realName", "彩亿");
        signMap.put("certNo", "201805");
        String signKey = "9188vlsppmh";
        String dataString = getFormDataParamMD5(signMap);
        System.out.println(sign(dataString, signKey, "UTF-8"));
    }

    public static String getFormDataParamMD5(Map<String, String> dataMap) {
        if (dataMap == null) return null;

        Set<String> keySet = dataMap.keySet();
        List<String> keyList = new ArrayList<String>(keySet);
        Collections.sort(keyList);

        StringBuilder builder = new StringBuilder();
        for (String key : keyList) {
            String value = dataMap.get(key);

            if (value != null && value.length() > 0) {
                // 最后一个元素
                if (key.equals(keyList.get(keyList.size() - 1))) {
                    builder.append(key + "=" + value);
                }else {
                    builder.append(key + "=" + value + "&");
                }
            }
        }
        return builder.toString();
    }

    public static String sign(String text, String key, String charset) {
        String message = text + key;

        MessageDigest digest = getDigest("MD5");
        digest.update(getContentBytes(message, charset));

        byte[] signed = digest.digest();

        return toHexString(signed);
    }

    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Not support:" + charset, ex);
        }
    }

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Not support:" + algorithm, ex);
        }
    }

    public static String toHexString(byte[] value) {
        if (value == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer(value.length * 2);
        for (int i = 0; i < value.length; i++) {
            sb.append(toHexString(value[i]));
        }
        return sb.toString();
    }

    public static String toHexString(byte value) {
        String hex = Integer.toHexString(value & 0xFF);

        return padZero(hex, 2);
    }

    private static String padZero(String hex, int length) {
        for (int i = hex.length(); i < length; i++) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }
}
