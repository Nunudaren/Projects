package cn.caijiajia.credittools.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by qmac on 17/3/1.
 * spring容器wrapper 从容器获得bean
 */
public class SpringContextWrapper implements ApplicationContextAware {

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =  applicationContext;
    }

    public static Object getBeanByName(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBeanByClz(Class clz){
        return (T)applicationContext.getBean(clz);
    }
}
