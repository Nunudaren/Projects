package cn.design_pattern.adapter_pattern.interface_adapter;

/**
 * @author winter
 * @date 2019-01-31
 */
public abstract class Wrapper implements Sourceable{

    public void method1() {
        System.out.println("method1...");
    }

    public void method2() {}
}
