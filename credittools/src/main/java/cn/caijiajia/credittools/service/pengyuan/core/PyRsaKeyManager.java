/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.service.pengyuan.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:chendong
 * @Date:2018/5/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PyRsaKeyManager implements RsaKeyManager{

    private String pyPublicKey;
    private String selfPrivateKey;
}