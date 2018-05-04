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
import cn.caijiajia.credittools.bo.LoanProductBo;
import cn.caijiajia.credittools.vo.LoanProductFilterVo;
import org.springframework.stereotype.Component;
import lombok.Data;

import java.util.Map;
import java.util.List;

/**
 * @Author:chendongdong
 * @Date:2018/5/2
 */
@AppConf
@Component
@Data
public class Configs {

    @ConfElement(name = "credittools_product_tags")
    private List<String> loanProductTags;

    @ConfElement(name = "loanproduct_loanproducts_guide_words")
    private String guideWords;

    @ConfElement(name = "loanproduct_loanproducts_filters")
    private LoanProductFilterVo loanProductFilters;

    @ConfElement(name = "loanproduct_loanproducts_products")
    private List<LoanProductBo> loanProducts;
}
