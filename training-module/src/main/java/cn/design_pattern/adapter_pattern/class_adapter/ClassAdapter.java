package cn.design_pattern.adapter_pattern.class_adapter;

import cn.design_pattern.adapter_pattern.Source;
import cn.design_pattern.adapter_pattern.Targetable;

/**
 * @author winter
 * @date 2019-01-31
 */
public class ClassAdapter extends Source implements Targetable {
    public void method2(){
        System.out.println("this is the targetable method!");
    }
}

