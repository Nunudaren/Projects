package cn.utils;

import cn.caijiajia.framework.threadlocal.ParameterThreadLocal;

public final class MokaUtil {

    public static String getUid() {
        String uid = ParameterThreadLocal.getUid();
        if (uid == null) {
            uid = "";
        }
        return uid;
    }

    public static String getName() {
        String name = ParameterThreadLocal.getName();
        if (name == null) {
            name = "";
        }
        return name;
    }
}
