/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liujianyang on 2018/5/4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductBo {

    private String id;

    private String name;

    private String iconUrl;

    private String mark;

    private Integer minAmount;

    private Integer maxAmount;

    private String feeRate;

    private String lendPeriod;

    private boolean isShowFeeRate;

    private String promotion;

    private String onlineTime;

    private String offlineTime;

    private String jumpUrl;

    private Integer rank;

    private List<String> tags;

    private BigDecimal annualRate;

    private Integer lendTime;

    private Integer passRate;

    private boolean isAmountFirst;

}