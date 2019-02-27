package cn.design_pattern.adapter_pattern.object_adapter;

import cn.design_pattern.adapter_pattern.Source;
import cn.design_pattern.adapter_pattern.Targetable;

/**
 * @author winter
 * @date 2019-01-31
 */
public class Wrapper implements Targetable {

    private Source source; // 这里用的是构造函数注入 在spring中可以用注解的方式：@Autowire

    public Wrapper(Source source) {
        super();
        this.source = source;
    }

    @Override
    public void method1() {
        // TODO Auto-generated method stub
        source.method1();
    }

    @Override
    public void method2() {
        // TODO Auto-generated method stub
        System.out.println("this is the targetable method!");
    };
}
