package cn.caijiajia.credittools.service.pengyuan.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
public class DateUtil {
    public static final String FORMAT = "yyyyMMddHHmmss";

    public DateUtil() {
    }

    public static String currentTimeStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT));
    }
}
