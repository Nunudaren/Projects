/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.form;

import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.framework.exceptions.CjjClientException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by liujianyang on 2018/4/27.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {

    private String id;

    private String productId;
    @NotBlank
    private String name;
    private String onLineTime;
    private String offLineTime;
    private String iconUrl;
    private String mark;
    private Integer minAmount;
    private Integer maxAmount;
    private String feeRate;
    private String lendPeriod;
    private Boolean showFeeRate;
    private String promotion;
    @NotBlank
    private String jumpUrl;
    private String tags;
    @NotNull
    private Double annualRate;
    @NotNull
    private Integer lendTime;
    @NotNull
    private Integer passRate;
    private String amountFirst;

    public void valid(){
        if(name.length() > 7 || (StringUtils.isNotEmpty(mark) && mark.length() > 7)){
            throw new CjjClientException(ErrorResponseConstants.FIELD_LENGTH_EXCESS_CODE, ErrorResponseConstants.FIELD_LENGTH_EXCESS_MSG);
        }
    }
}
