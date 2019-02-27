package cn.encrpyt;

import cn.data_structure.tree.BinaryTree;
import com.amazonaws.services.dynamodbv2.xspec.M;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;

import java.security.MessageDigest;
import java.util.*;

public class StringUtil {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultStr = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultStr.append(byteToHexString(b[i]));
        }
        return resultStr.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        String resultStr = null;
        try {
            resultStr = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultStr = byteArrayToHexString(md.digest(resultStr.getBytes()));
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return resultStr;
    }

    /**
     * @param s
     * @param args
     * @return
     */
    public static void joinStr(String s, int... args) {
        int a = args[0];
        int b = args[1];
        String c = "a";
        List<Integer> str = Lists.newArrayList(a, b);
        for (Object d : str
                ) {
            System.out.println(d);
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        System.out.println(MD5Encode("123456"));
        int a = 2;
        int b = 3;
        int[] number = new int[]{1, 2};
        joinStr("hi", a, b);
        joinStr("nihai", number);

        int i = 2;
        switch(i){
            case 0:
                System.out.println("0");
                break;
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
            case 3:
                System.out.println("3");
                break;
            case 4:
                System.out.println("4");
            default:
                System.out.println("default");
        }

        System.out.println("UN_JINGYING_8_rate_desc".length());
//        Map<String, String> stringMap = Maps.newHashMap();
//        stringMap.put("abc", "234");
//        stringMap.put("abc", "23");
//        stringMap.put("abc", "nihao");
//        stringMap.put("ab-c", "nihao");
//        stringMap.values();
//        Iterator<Map.Entry<String,String>> iterator = stringMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String,String> entry = iterator.next();
//            System.out.println(entry.getKey() + entry.getValue());
//        }
//
//        for (Map.Entry<String, String> entryStr : stringMap.entrySet()) {
//            System.out.println(entryStr.getKey() + entryStr.getValue());
//        }

    }
}
