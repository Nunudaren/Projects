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

    @ConfElement(name = "loanproduct_loanproducts_products")
    private List<LoanProductBo> loanProducts;

    @ConfElement(name = "loanmarket_union_login_url")
    private Map<String, String> unionLoginUrl;

    @ConfElement(name = "loanproduct_loanproducts_filters")
    private LoanProductFilterVo loanProductFilters;

    @ConfElement(name = "credittools_union_login_products")
    private List<String> unionLoginProducts;

    @ConfElement(name = "credittools_external_url")
    private String externalUrl;
    @ConfElement(name = "credittools_click_num_switch")
    private Integer clickNumSwitch; // 0：关闭 1：打开

    @ConfElement(name = "credittools_product_click_num")
    private Map<String, Integer> productClickNum;

    @ConfElement(name = "credittools_click_num_desp")
    private String clickNumDesp;

    @ConfElement(name = "credittools_client_proxy_url")
    private String credittoolsUrl;

    @ConfElement(name = "credittools_default_out_of_time")
    private Integer defaultOutOfTime;

    @ConfElement(name = "credittools_user_recommend_prop")
    private List<String> userRecommentProp;

    @ConfElement(name = "credittools_hb_union_login_url")
    private String hbUnionLoginUrl;
}
