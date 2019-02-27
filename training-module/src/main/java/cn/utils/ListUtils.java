package cn.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyangbo
 * @description
 * @Date：Created in 10:14 2018/6/4
 */
public class ListUtils {

    public static List<String> parseString(String s){
        List<String> list;
        if (StringUtils.isEmpty(s)){
            list = Lists.newArrayList();
        }else {
            String[] sArr = s.split(",");
            list = Arrays.asList(sArr);
        }
        return list;
    }
}
