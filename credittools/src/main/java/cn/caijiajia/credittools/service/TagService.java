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

import cn.caijiajia.credittools.domain.Tag;
import cn.caijiajia.credittools.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liujianyang on 2018/4/28.
 */
@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public Tag getTagById(Integer id) {
        return tagMapper.selectByPrimaryKey(id);
    }

}
