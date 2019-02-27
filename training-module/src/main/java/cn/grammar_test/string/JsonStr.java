package cn.grammar_test.string;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author:chendong
 * @Date:2018/9/11
 */
@Slf4j
public class JsonStr {

    @Test
    public void jsonStrTest() {
        String jsonStr = "{\n" +
                "  \"mobile\": \"13501953683\",\n" +
                "  \"messageContent\": \"GZ1\",\n" +
                "  \"channel\": null,\n" +
                "  \"messageCode\": 2\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String mobile = jsonObject.getString("mobile");
        String channel = jsonObject.getString("channel");
        Integer unknown = jsonObject.getInteger("messageCode");

        System.out.println(mobile);
        System.out.println(channel);
        System.out.println(unknown);

        String s = null;
//        Integer i = Integer.parseInt(s);
        Integer a = Integer.valueOf(s);
//        System.out.println(a);
    }
}
