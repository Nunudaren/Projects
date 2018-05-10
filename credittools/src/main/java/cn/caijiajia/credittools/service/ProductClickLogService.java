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

import cn.caijiajia.credittools.bo.ProductClickNumBo;
import cn.caijiajia.credittools.mapper.ProductClickLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liujianyang on 2018/5/9.
 */
@Service
@Slf4j
public class ProductClickLogService {

    @Autowired
    private ProductClickLogMapper productClickLogMapper;

    public List<ProductClickNumBo> getProductClickNum(){
        return productClickLogMapper.getProductNum();
    }

}
