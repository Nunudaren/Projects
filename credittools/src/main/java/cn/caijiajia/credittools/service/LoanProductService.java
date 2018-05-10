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

import cn.caijiajia.credittools.bo.LoanProductFilterBo;
import cn.caijiajia.credittools.bo.ProductClickNumBo;
import cn.caijiajia.credittools.bo.UnionJumpBo;
import cn.caijiajia.credittools.bo.UnionLoginBo;
import cn.caijiajia.credittools.common.constant.CredittoolsConstants;
import cn.caijiajia.credittools.common.constant.ErrorResponseConstants;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.ProductFilterTypeEnum;
import cn.caijiajia.credittools.constant.ProductSortEnum;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductClickLog;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.form.ProductListClientForm;
import cn.caijiajia.credittools.mapper.ProductClickLogMapper;
import cn.caijiajia.credittools.mapper.ProductMapper;
import cn.caijiajia.credittools.utils.MoneyUtil;
import cn.caijiajia.credittools.vo.LoanProductFilterVo;
import cn.caijiajia.credittools.vo.ProductClientVo;
import cn.caijiajia.credittools.vo.ProductListClientVo;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.threadlocal.ParameterThreadLocal;
import cn.caijiajia.loanproduct.common.Resp.LoanProductResp;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liujianyang on 2018/5/9.
 */
@Service
@Slf4j
public class LoanProductService {

    @Autowired
    private Configs configs;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserRpc userRpc;

    @Autowired
    private UnionLoginLogService unionLoginLogService;

    @Autowired
    private ProductsFactory productsFactory;

    @Autowired
    private LoanProductMgrService loanProductMgrService;

    @Autowired
    private ProductClickLogMapper productClickLogMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    public LoanProductFilterVo getLoanProductFilter() {
        LoanProductFilterVo rtnVo = new LoanProductFilterVo();
        if (null != configs.getLoanProductFilters()) {
            rtnVo = configs.getLoanProductFilters();
        }
        for (LoanProductFilterBo loanProductFilterBo : rtnVo.getFilters()) {
            if (ProductFilterTypeEnum.PRODUCT_TAGS == loanProductFilterBo.getType()) {
                Set<String> usedTags = getUsedTags();
                List<LoanProductFilterBo.Option> options = transform(usedTags);
                loanProductFilterBo.setOptions(options);
            }
        }
        return rtnVo;
    }

    private Set<String> getUsedTags() {
        Set<String> set = Sets.newHashSet();
        List<String> tags = configs.getLoanProductTags();
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS);
        List<Product> products = productMapper.selectByExample(productExample);
        if (CollectionUtils.isNotEmpty(products)) {
            for (Product product : products) {
                List<String> tagids = Lists.newArrayList(product.getTags().split(CredittoolsConstants.SPLIT_MARK));
                for (String tagid : tagids) {
                    if (tags.contains(tagid)) {
                        set.add(tagid);
                    }
                    if (set.size() == tags.size()) {
                        return set;
                    }
                }
            }
        }

        return set;
    }


    private List<LoanProductFilterBo.Option> transform(Set<String> usedTags) {
        Collection<LoanProductFilterBo.Option> transform = Collections2.transform(usedTags, new Function<String, LoanProductFilterBo.Option>() {
            @Override
            public LoanProductFilterBo.Option apply(String input) {
                return LoanProductFilterBo.Option.builder().desp(input).value(input).build();
            }
        });
        return Lists.newArrayList(transform);
    }

    public ProductListClientVo getProductListClient(ProductListClientForm productListClientForm) {
        String guideWords = null;
        List<Product> products = getProducts(productListClientForm);
        if (null != configs.getGuideWords()) {
            guideWords = configs.getGuideWords();
        }
        List<ProductClientVo> rtnVo = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(products)) {
            Map<String, Integer> clickNum = getClickNum();
            rtnVo = transform(products, clickNum);
        }
        return ProductListClientVo.builder()
                .loanProductList(rtnVo)
                .guideWords(guideWords)
                .build();
    }

    private Map<String, Integer> getClickNum() {
        Map<String, Integer> productUserNum = Maps.newHashMap();
        if (1 == configs.getClickNumSwitch()) {
            productUserNum = configs.getProductClickNum();
        }
        List<ProductClickNumBo> productClickNum = getProductClickNum();
        if(CollectionUtils.isEmpty(productClickNum) && MapUtils.isEmpty(productUserNum)){
            throw new CjjClientException(ErrorResponseConstants.GET_USER_CLICK_NUM_FAILED_CODE, ErrorResponseConstants.GET_USER_CLICK_NUM_FAILED_MSG);
        }
        if(CollectionUtils.isEmpty(productClickNum)){
            return productUserNum;
        }
        for (ProductClickNumBo productClickNumBo : productClickNum) {
            productUserNum.put(productClickNumBo.getProductId(), productClickNumBo.getClickNum() + (productUserNum.get(productClickNumBo.getProductId()) == null ? 0 : productUserNum.get(productClickNumBo.getProductId())));
        }
        return productUserNum;
    }

    private List<ProductClickNumBo> getProductClickNum(){
        return productClickLogMapper.getProductNum();
    }

    private List<Product> getProducts(ProductListClientForm productListClientForm) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS);
        if (StringUtils.isEmpty(productListClientForm.getSortValue()) || ProductSortEnum.DEFAULT.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("rank asc");
        } else if (ProductSortEnum.LEND_FAST.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("lend_time asc");
        } else if (ProductSortEnum.FEE_RATE_LOW.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("annual_rate asc");
        } else if (ProductSortEnum.PASS_RATE_HIGH.getValue().equals(productListClientForm.getSortValue())) {
            productExample.setOrderByClause("pass_rate desc");
        }
        if (null == productListClientForm.getFilterType() || StringUtils.isEmpty(productListClientForm.getFilterValue())) {
            return productMapper.selectByExample(productExample);
        }
        if (ProductFilterTypeEnum.LEND_AMOUNT == productListClientForm.getFilterType()) {
            String[] filterAmount = StringUtils.split(productListClientForm.getFilterValue(), ",");
            Integer filterMinAmount = Integer.parseInt(filterAmount[0]);
            Integer filterMaxAmount = Integer.parseInt(filterAmount[1]);

            criteria.andMinAmountLessThanOrEqualTo(filterMaxAmount).andMaxAmountGreaterThanOrEqualTo(filterMinAmount);
            return productMapper.selectByExample(productExample);

        } else if (ProductFilterTypeEnum.PRODUCT_TAGS == productListClientForm.getFilterType()) {

            criteria.andTagsLike("%" + productListClientForm.getFilterValue() + "%");
            return productMapper.selectByExample(productExample);
        }

        return null;
    }

    private List<ProductClientVo> transform(List<Product> loanProducts, Map<String, Integer> clickNum) {
        List<ProductClientVo> products = Lists.newArrayList();
        for (Product product : loanProducts) {
            products.add(ProductClientVo.builder()
                    .feeRate(product.getShowFeeRate() ? product.getFeeRate() : null)
                    .iconUrl(product.getIconUrl())
                    .id(product.getProductId())
                    .jumpUrl(product.getJumpUrl() + "&id=" + product.getId() + (ParameterThreadLocal.getUid() == null ? "" : "&p_u=" + ParameterThreadLocal.getUid()))
                    .mark(product.getMark())
                    .name(product.getName())
                    .clickNum(formatClickNumStr(clickNum.get(product.getProductId())))
                    .promotion(product.getPromotion())
                    .optionalInfo(Lists.newArrayList(Collections2.transform(buildOptionalInfo(product), new Function<LoanProductResp.OptionalInfo, ProductClientVo.OptionalInfo>() {
                        @Override
                        public ProductClientVo.OptionalInfo apply(LoanProductResp.OptionalInfo input) {
                            return ProductClientVo.OptionalInfo.builder().type(input.getType()).value(input.getValue()).build();
                        }
                    })))
                    .build());
        }
        return products;
    }

    private String formatClickNumStr(Integer num) {
        DecimalFormat df = new DecimalFormat("#.0");
        if (null == num || 0 == num) {
            return 0 + configs.getClickNumDesp();
        }
        String clickNum = num.toString();
        if (num > 10000) {
            clickNum = df.format(Double.valueOf(num) / 10000) + "万";
        }
        return clickNum + configs.getClickNumDesp();
    }

    private List<LoanProductResp.OptionalInfo> buildOptionalInfo(Product product) {
        List<LoanProductResp.OptionalInfo> optionalInfo = Lists.newArrayList();
        Integer minAmount = product.getMinAmount();
        Integer maxAmount = product.getMaxAmount();
        optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                .type("额度范围")
                //最低额度与最高额度相等时只显示一个额度值
                .value(minAmount.intValue() == maxAmount.intValue() ?
                        MoneyUtil.decimalFormat(maxAmount) + "元" :
                        MoneyUtil.decimalFormat(minAmount) + "-" + MoneyUtil.decimalFormat(maxAmount) + "元")
                .build());
        if (product.getShowFeeRate()) {
            optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                    .type("参考利率")
                    .value(product.getFeeRate())
                    .build());
        } else {
            optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                    .type("借款周期")
                    .value(product.getLendPeriod())
                    .build());
        }
        if (!product.getAomountFirst()) {
            LoanProductResp.OptionalInfo temp = optionalInfo.get(0);
            optionalInfo.set(0, optionalInfo.get(1));
            optionalInfo.set(1, temp);
        }
        return optionalInfo;
    }

    public void redirectUrl(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("p_u");
        String key = request.getParameter("key");
        String jumpUrl = loanProductMgrService.getUnionLoginUrl(key);
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(jumpUrl)){
            redirectLoanProductUrl(request, response);
            return;
        }
        UnionJumpBo jumpBo;
        if (StringUtils.isEmpty(uid)) {
            jumpBo = UnionJumpBo.builder().jumpUrl(loanProductMgrService.getUnionLoginUrl(key)).build();
        } else {
            String mobile = getMobileNoCheck(uid);
            IProductsService productsService = productsFactory.getProductService(key);
            jumpBo = productsService.unionLogin(uid, key);

            final String channelName = productsService.getChannelName();

            unionLoginLogService.addUnionLoginLog(UnionLoginBo.builder().uid(uid).channel(channelName).mobile(mobile).isOldUser(jumpBo.getIsOldUser()).build());
        }

        try {
            response.sendRedirect(jumpBo.getJumpUrl());
        } catch (IOException e) {
            throw new CjjClientException(ErrorResponseConstants.REDIRECT_FAILED_CODE, ErrorResponseConstants.REDIRECT_FAILED_MSG);
        }
    }

    private String getMobileNoCheck(String uid) {
        UserVo userInfo = userRpc.getUserInfo(uid);
        if (userInfo == null) {
            return null;
        }
        return userInfo.getMobile();
    }

    private void redirectLoanProductUrl(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("p_u");
        String id = request.getParameter("id");

        taskExecutor.execute(new ClickTimeInc(uid, id));
        try {
            response.sendRedirect(getJumpUrl(Integer.valueOf(id)));
        } catch (IOException e) {
            throw new CjjClientException(ErrorResponseConstants.REDIRECT_FAILED_CODE, ErrorResponseConstants.REDIRECT_FAILED_MSG);
        }

    }

    public class ClickTimeInc implements Runnable{

        private String uid;

        private String id;

        public ClickTimeInc(String uid, String id){
            this.uid = uid;
            this.id = id;
        }

        @Override
        public void run() {
            ProductClickLog productClickLog = new ProductClickLog();
            productClickLog.setUid(uid);
            productClickLog.setProductId(Integer.valueOf(id));
            productClickLogMapper.insertAndClickTimeIncre(productClickLog);
        }
    }

    private String getJumpUrl(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        if(product == null){
            throw new CjjClientException(ErrorResponseConstants.PRODUCT_NOT_FOUND_CODE, ErrorResponseConstants.PRODUCT_NOT_FOUND_MSG);
        }
        return product.getJumpUrl();
    }

}
