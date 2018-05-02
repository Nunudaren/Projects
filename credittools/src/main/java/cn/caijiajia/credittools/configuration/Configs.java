/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.configuration;

import cn.caijiajia.confplus.client.annotation.AppConf;
import cn.caijiajia.confplus.client.annotation.ConfElement;
import lombok.Data;

import java.util.Map;

/**
 * Created by liujianyang on 2018/5/2.
 */
@AppConf
@Data
public class Configs {
    @ConfElement(name = "credittools_tags")
    private Map<String, String> tags;
}
