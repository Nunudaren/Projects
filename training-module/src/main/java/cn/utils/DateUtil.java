package cn.utils;

import org.joda.time.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:chendong
 * @Date:2018/5/22
 */
public class DateUtil {
    public static final String ALL = "yyyyMMdd HH:mm:ss";
    public static final String NYR = "yyyy.MM.dd";
    public static final String DATE_FORMAT_WITH_HYPHEN = "yyyy-MM-dd HH:mm:ss";
    public static final String NYRSF = "yyyyMM/dd HH:mm";
    public static final String NYRSFM = "yyyy.MM.dd HH:mm:ss";
    public static final String YR = "MM月dd号";

    public static String convert2Str(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date convert2Date(String str, String format) {
        if (str == null || "".equals(str)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Integer diffMinutes2(DateTime firstDate, DateTime secondDate) {
        return Minutes.minutesBetween(firstDate,secondDate).getMinutes()  % 60;
    }

    public static Integer diffMinutes(Timestamp firstTime, Timestamp secondTime) {
        Interval interval = new Interval(firstTime.getTime(), secondTime.getTime());
        return interval.toPeriod().getMinutes();
    }

    public static void main(String[] args) {

        DateTime fistDate = new DateTime(1527498658 * 1000l);
        System.out.println(fistDate.isBefore(null));
        DateTime secondDate = new DateTime(1527499018 * 1000l);
        System.out.println(secondDate);
        System.out.println(diffMinutes2(fistDate,secondDate));

        Interval interval = new Interval(1527498658 * 1000l, 1527499018 * 1000l);
        System.out.println(interval.toPeriod().getMinutes());

        System.out.println(new Timestamp(new Date().getTime()));
    }

    @Test
    public void test() {
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        System.out.println(LocalTime.now());

        Date now = new Date();
        Date before = new Date(now.getTime() - 500 * 1000L);
        System.out.println(DateUtil.convert2Str(now, DATE_FORMAT_WITH_HYPHEN));
        System.out.println(DateUtil.convert2Str(before, DATE_FORMAT_WITH_HYPHEN));

    }

}
