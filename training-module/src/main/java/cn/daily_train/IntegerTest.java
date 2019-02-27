package cn.daily_train;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chendong
 * @Date:2018/9/14
 */
public class IntegerTest {
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE > 0);
        List<Integer> forList = new ArrayList<>();
        forList.add(1);
        forList.add(2);
        forList.add(3);

        for (Integer loop: forList
             ) {
            if (loop.equals(2)) {
                continue;
            }
            System.out.println(loop);
        }
    }
}
