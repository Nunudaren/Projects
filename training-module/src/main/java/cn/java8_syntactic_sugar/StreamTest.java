package cn.java8_syntactic_sugar;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:chendong
 * @Date:2018/8/20
 */
public class StreamTest {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        Arrays.stream(arr)
                .map(s -> {
                    System.out.println("map: " + s);
                    return s;
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s == 2 ;
                });

        Stream.of("d2", "a3", "a2", "b2", "c" )
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
        Stream.of("d2", "a3", "a2", "b2", "c" )
                .filter(s -> {
                    return !"d2".equals(s);
                })
//                .collect(Collectors.toList())
                .forEach(s -> {
                    s = s + "d";
                    System.out.println("anyMatch: " + s);
                });

       boolean b =  Stream.of("d2", "a3", "a2", "b2", "c" )
                .filter(s -> {
                    return !"d2".equals(s);
                })
//               .filter(s -> !"d2".equals(s))
                .anyMatch(s ->
                    s.equals("d2")
                );
        System.out.println(b);


        System.out.println( Stream.of(1, 2, 3, 4, 5 )
                .sorted((o1, o2) -> o1.compareTo(o2))
                .findFirst()
                .get());
    }
}
