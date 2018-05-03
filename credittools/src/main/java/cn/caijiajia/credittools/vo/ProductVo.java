/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by liujianyang on 2018/4/28.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private Integer id;

    private String productId;
    private String name;
    private String iconUrl;
    private String mark;
    private Integer minAmount;
    private Integer maxAmount;
    private String feeRate;
    private String lendPeriod;
    private Boolean showFeeRate;
    private String promotion;
    private String jumpUrl;
    private List<String> tags;
    private Double annualRate;
    private Double lendTime;
    private Double passRate;
    private Boolean amountFirst;
    private List<String> configTags;

}
