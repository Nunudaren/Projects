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

import cn.caijiajia.credittools.bo.UnionLoginBo;
import cn.caijiajia.credittools.domain.UnionLoginLog;
import cn.caijiajia.credittools.mapper.UnionLoginLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liujianyang on 2018/4/27.
 */
@Service
@Slf4j
public class UnionLoginLogService {

    @Autowired
    private UnionLoginLogMapper unionLoginLogMapper;

    public void addUnionLoginLog(UnionLoginBo unionLoginBo){
        UnionLoginLog unionLoginLog = new UnionLoginLog();
        unionLoginLog.setUid(unionLoginBo.getUid());
        unionLoginLog.setChannel(unionLoginBo.getChannel());
        unionLoginLog.setMobile(unionLoginBo.getMobile());
        unionLoginLog.setOldUser(unionLoginBo.getIsOldUser());
        unionLoginLogMapper.insertSelective(unionLoginLog);
    }
}
