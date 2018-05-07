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

import cn.caijiajia.credittools.bo.UnionJumpBo;

/**
 * Created by liujianyang on 2018/5/4.
 */
public interface IProductsService {
    // 联合登陆，获得jumpUrl
    UnionJumpBo unionLogin(String uid, String key);

    String getChannelName();
}
