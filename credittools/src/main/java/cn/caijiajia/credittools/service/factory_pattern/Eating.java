package cn.caijiajia.credittools.service.factory_pattern;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 将创建类实例对象封装到不同的工厂类中，并让工厂类都实现统一的接口；
 */
@Service
@Slf4j
public class Eating implements Provider {

    @Override
    public void action() {
        System.out.println("eating...");
    }
}
