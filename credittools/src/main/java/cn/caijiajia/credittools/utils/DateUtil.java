package cn.caijiajia.credittools.utils;

import cn.caijiajia.framework.exceptions.CjjClientException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
public class DateUtil {
    public static final String ALL = "yyyyMMdd HH:mm:ss";
    public static final String NYR = "yyyy.MM.dd";
    public static final String DATE_FORMAT_WITH_HYPHEN = "yyyy-MM-dd";
    public static final String NYRSF = "yyyy/MM/dd HH:mm";
    public static final String NYRSFM = "yyyy.MM.dd HH:mm:ss";
    public static final String YR = "MM月dd号";

    //返回的时间是当天0点,并不是前一天23点59分59秒
    public static Date getStartDateTimeOfDay(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().toDate();
    }

    public static DateTime getEndTimeOfDay(Date date) {
        return new DateTime(date).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
    }

    public static DateTime getStartTimeOfDay(Date date) {
        return new DateTime(date).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
    }

    public static Date addMinute(Date date, int minutes) {
        return new DateTime(date).plusMinutes(minutes).toDate();
    }

    public static Date addDate(Date date, int days) {
        return new DateTime(date).plusDays(days).toDate();
    }


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

    public static String convert2Str(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    public static int compareDate(Date first, Date second) {
        LocalDate firstDate = new LocalDate(first);
        LocalDate secondDate = new LocalDate(second);

        if (firstDate.isBefore(secondDate)) {
            return -1;
        } else if (firstDate.isEqual(secondDate)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 日期加减
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addOrSubtractDates(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + count);//
        return calendar.getTime();
    }

    /**
     *日期字符串格式转换
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String formatDateStr(String dateStr, String format) {
        Date date = convert2Date(dateStr, "yyyyMMddHHmmSS");
        return convert2Str(date, format);
    }
}
