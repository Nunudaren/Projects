package cn.design_pattern.adapter_pattern.interface_adapter;

/**
 * @author winter
 * @date 2019-01-31
 */
public class SourceSub1 extends Wrapper {
    public void method1(){
        super.method1();
        System.out.println("the sourceable interface's first Sub1!");
    }
}
