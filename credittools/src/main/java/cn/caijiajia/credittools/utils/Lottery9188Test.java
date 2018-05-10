package cn.caijiajia.credittools.utils;


import cn.caijiajia.credittools.service.Lottery9188UnionLoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhouyangbo
 * @description
 * @Date：Created in 下午3:50 2018/4/19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class Lottery9188Test {

    @Autowired
    private Lottery9188UnionLoginService lottery9188UnionLoginService;

    @Test
    public void test(){
        lottery9188UnionLoginService.unionLogin("111","");
    }

}
