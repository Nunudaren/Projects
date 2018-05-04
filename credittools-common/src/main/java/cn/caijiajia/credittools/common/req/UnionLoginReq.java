/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.common.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Harry on 2018/4/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnionLoginReq {

    private String id; // e.g 00036

    private String mobile;

    private String imei;

    private String ip;

    private String lbsLocation; // 经纬度信息

}