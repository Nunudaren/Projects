package cn.data_structure.list_test;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.event.EventConstants;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrainDemOne implements Serializable {

//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        System.out.println(isNumeric("1.2e-2"));
//
//    }

    public static boolean isNumeric(String str) {
        String string = str;
        return string.matches("[\\+-]?[0-9]*(\\.[0-9]*)?([eE][\\+-]?[0-9]+)?");
    }

    /**
     * 使用集合转数组的方法，必须使用集合的 toArray(T[] array)，传入的是类型完全
     * 一样的数组，大小就是 list.size()。
     * 使用 toArray 带参方法，入参分配的数组空间不够大时，toArray 方法内部将重新分配
     * 内存空间，并返回新数组地址;如果数组元素个数大于实际所需，下标为[ list.size() ]
     * 的数组元素将被置为 null，其它数组元素保持原值，因此最好将方法入参数组大小定义与集
     * 合元素个数一致
     */
    @org.junit.Test
    public void listToArrayToStr() {
        List<String> listStr = new ArrayList<String>(3);
        listStr.add("hello");
        listStr.add("world");
        listStr.add("!!!");

        //直接使用 toArray 无参方法存在问题，此方法返回值只能是 Object[]类，若强转其它 类型数组将出现 ClassCastException 错误。
        String[] str2 = (String[]) listStr.toArray();  //Exception in thread "main" java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;

        String[] array = new String[listStr.size()];
        array = listStr.toArray(array);

        String convertStr = StringUtils.join(array, ",");
        System.out.println(convertStr);
    }

    /**
     * url param join eg. "key=value&key=value"
     *
     * @param requestParams
     * @return
     */
    public static String convertParamMapToQueryString(Map<String, String> requestParams) {
        List<String> keyValuePairs = com.google.common.collect.Lists.newArrayListWithExpectedSize(requestParams.size());
        for (Map.Entry<String, String> requestParamEntry : requestParams.entrySet()) {
            keyValuePairs.add(requestParamEntry.getKey() + "=" + requestParamEntry.getValue());
        }

        // StringUtils.join(new String[list.size]);
        return StringUtils.join(keyValuePairs.toArray(new String[keyValuePairs.size()]), "&");

    }

    @org.junit.Test
    public void joinListStr() {
        List<Integer> clickProductIds = new ArrayList<>();
        clickProductIds.add(9);
        clickProductIds.add(12);
      /*  clickProductIds.add(12);
        clickProductIds.add(10);*/
        clickProductIds.add(10);
        String joinStr = Joiner.on(",").join(clickProductIds);
        System.out.println(joinStr);
        String clusterName = System.getenv().get("env");
        String env = System.getProperty("env");

        System.out.println(clusterName);
        System.out.println(env);
    }

    /**
     * java 8 字符串拼接器
     */
    @Test
    public void JoinStrArrJava8() {
        // 定义人名数组
        final String[] names = {"Zebe", "Hebe", "Mary", "July", "David"};

        // apache method 1 :
        String convertStr = StringUtils.join(names, ",");

        // java 8 stream
        Stream<String> stream1 = Stream.of(names);
        Stream<String> stream2 = Stream.of(names);
        Stream<String> stream3 = Stream.of(names);
        // 拼接成 [x, y, z] 形式
        String result1 = stream1.collect(Collectors.joining(", ", "[", "]"));
        // 拼接成 x | y | z 形式
        String result2 = stream2.collect(Collectors.joining(" | ", "", ""));
        // 拼接成 x -> y -> z] 形式
        String result3 = stream3.collect(Collectors.joining(" -> ", "", ""));

        System.out.println(convertStr);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }


    /**
     * 使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方 法，它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常。
     * 说明:asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。Arrays.asList 体现的是适配器模式，只是转换接口，后台的数据仍是数组。
     * 第一种情况:list.add("yangguanbao"); 运行时异常。
     * 第二种情况:str[0] = "gujin"; 那么 list.get(0)也会随之修改。
     */
    @org.junit.Test
    public void arrayToList() {
        String[] str = new String[]{"you", "wu", "同意"};

//        asList 的返回对象是一个 Arrays 内部类
        List list = Arrays.asList(str);
        System.out.println(list.get(0));

        String callResult = "同意";
//        String callResult = "同意 - 同意借款";
        System.out.println(Arrays.asList(callResult.split("-")));
        System.out.println(callResult.split("-")[0]);
        System.out.println(list.contains(callResult.split("-")[0].trim()));

    }

    @org.junit.Test
    public void splitStr() throws FileNotFoundException {
        String str = "a,b,c,,";
        String[] ary = str.split(","); // 预期大于 3，结果是 3
        System.out.println(ary.length);
    }


    /**
     * 空集合 for 循环 不会抛空指针
     */
    @org.junit.Test
    public void nullList() {
        List<String> listStr = Lists.newArrayList();
//        listStr.add("a");
        for (String s : listStr) {
            System.out.println(s);
        }
    }


    /**
     * SimpleDateFormat 线程不安全的类；一般不要定义为 static 变量
     * 如果定义为 static ，必须加锁，或者使用DateUtils工具类，如下处理
     */
    public static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    @org.junit.Test
    public void indexOfStr() {
        final char ESCAPE_CHAR = '\\';
        String messagePattern = "chendong\\{}dong";
        int delimeterStartIndex = messagePattern.indexOf("{}", 0);
        boolean bl = messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR;
        System.out.println(bl);

        //格式化字符串 java.text.MessageFormat
        String who = MessageFormat.format("hi, welcome {0}{1}", "chendong", "dong");
        System.out.println(who);
    }

    /**
     * examples for showing how to use org.slf4j.helpers.FormattingTuple
     *
     * @param event
     * @param julLevel
     * @return
     */
    private LogRecord eventToRecord(LoggingEvent event, Level julLevel) {
        String format = event.getMessage();
        Object[] arguments = event.getArgumentArray();
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        if (ft.getThrowable() != null && event.getThrowable() != null) {
            throw new IllegalArgumentException("both last element in argument array and last argument are of type Throwable");
        }

        Throwable t = event.getThrowable();
        if (ft.getThrowable() != null) {
            t = ft.getThrowable();
            throw new IllegalStateException("fix above code");
        }

        LogRecord record = new LogRecord(julLevel, ft.getMessage());
        record.setLoggerName(event.getLoggerName());
        record.setMillis(event.getTimeStamp());
        record.setSourceClassName(EventConstants.NA_SUBST);
        record.setSourceMethodName(EventConstants.NA_SUBST);

        record.setThrown(t);
        return record;
    }


}
