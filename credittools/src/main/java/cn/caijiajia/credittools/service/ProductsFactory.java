/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.service;

import cn.caijiajia.credittools.service.pengyuan.PengyuanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

/**
 * Created by liujianyang on 2018/5/4.
 */
@Service
@Slf4j
public class ProductsFactory {

    public <T> T getProductService(String key) {
        Class clz = null;
        switch (key) {
            case "qihu" :
                clz = QihuService.class;
                break;
            case "youyu" :
                clz = YouyuUnionLoginService.class;
                break;
            case "pengyuan":
                clz = PengyuanService.class;
                break;
            case "9188" :
                clz = Lottery9188UnionLoginService.class;
                break;
        }
        return (T)ContextLoader.getCurrentWebApplicationContext().getBean(clz);
    }
}