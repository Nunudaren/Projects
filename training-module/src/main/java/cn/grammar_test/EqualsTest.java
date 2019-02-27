package cn.grammar_test;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;


public class EqualsTest {
    public static void main(String[] args) {
        LinkedList<Student> list = new LinkedList<Student>();
        Set<Student> set = new HashSet<Student>();
        Student stu1 = new Student(3, "张三");
        Student stu2 = new Student(3, "张三");
        System.out.println("stu1 == stu2 : " + (stu1 == stu2));  //比较两个对象是否指向同一块存储空间，重写了hashcode()方法之后，虽然两个对象的hashcode()相同了，但是对象的存储地址仍然不同
        System.out.println("stu1.equals(stu2) : " + stu1.equals(stu2));
        System.out.println("hashcode: " + stu1.hashCode());
        System.out.println("hashcode: " + stu2.hashCode());
        list.add(stu1);
        list.add(stu2);
        System.out.println("list size:" + list.size());

        set.add(stu1);
        set.add(stu2);
        System.out.println("set size:" + set.size());
    }

    @Test
    public void demo_StrSplit() {
//        String str = "AC_NEW_REOO";
//        String[] strArr1 = str.split("_", 1);
//        String[] strArr2 = str.split("_", 2);
//        String[] strArr3 = str.split("_", 4);
//        System.out.println(Arrays.asList(strArr1));
//        System.out.println(Arrays.asList(strArr2));
//        System.out.println(Arrays.asList(strArr3));
//
//        System.out.println(10000 / (60 * 24));
//        BigDecimal i = BigDecimal.valueOf(100 / (60 * 24));
//        BigDecimal b = i.setScale(2, RoundingMode.HALF_UP);
//        System.out.println(b);


        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(5);

        Date date = localDateTime.toDate();
        System.out.println(date);
        Timestamp timeNode = new Timestamp(date.getTime());
        System.out.println(timeNode);
    }

} 