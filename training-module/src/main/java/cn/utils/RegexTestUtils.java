package cn.utils;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author winter
 * @date 2019-01-28
 */
public class RegexTestUtils {

    @Test
    public void testDemoOne() {
        System.out.println(isMobile("13501953683"));

        List<String> mobileList = Lists.newArrayList();
        mobileList.add("13501953683");
        mobileList.add("12345367892");
        List<String> checkMobile = mobileList.stream().filter(s -> isMobile(s)).collect(Collectors.toList());
        System.out.println(checkMobile);
    }

    /**
     * 判断是否是手机号
     *
     * @param mobile
     * @return
     */
    public boolean isMobile(String mobile) {
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }


}
