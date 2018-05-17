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
import cn.caijiajia.credittools.common.req.Lattery9188CheckUserReq;
import cn.caijiajia.credittools.common.constant.ProductFilterTypeEnum;
import cn.caijiajia.credittools.common.req.ProductListClientReq;
import cn.caijiajia.credittools.common.resp.ProductClientResp;
import cn.caijiajia.credittools.common.resp.ProductListClientResp;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.ProductSortEnum;
import cn.caijiajia.credittools.delegator.UserDelegator;
import cn.caijiajia.credittools.domain.Product;
import cn.caijiajia.credittools.domain.ProductClickLog;
import cn.caijiajia.credittools.domain.ProductClickLogExample;
import cn.caijiajia.credittools.domain.ProductExample;
import cn.caijiajia.credittools.mapper.ProductClickLogMapper;
import cn.caijiajia.credittools.mapper.ProductMapper;
import cn.caijiajia.credittools.utils.MoneyUtil;
import cn.caijiajia.credittools.vo.LoanProductFilterVo;
import cn.caijiajia.framework.exceptions.CjjClientException;
import cn.caijiajia.framework.threadlocal.ParameterThreadLocal;
import cn.caijiajia.loanproduct.common.Resp.LoanProductResp;
import cn.caijiajia.redis.client.RedisClient;
import cn.caijiajia.user.common.resp.UserVo;
import cn.caijiajia.user.rpc.UserRpc;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

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
    private UserDelegator userDelegator;

    @Autowired
    private ProductClickLogMapper productClickLogMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private RedisClient redisClient;

    public static final String REDIRECT_URL = "/redirectUrl";

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

    public ProductListClientResp getProductListClient(ProductListClientReq productListClientReq) {
        String guideWords = null;
        if (null != configs.getGuideWords()) {
            guideWords = configs.getGuideWords();
        }
        List<Product> products = getProducts(productListClientReq);
        List<ProductClientResp> rtnVo = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(products)) {
            Map<String, Integer> clickNumMap = getClickNumMap();
            rtnVo = transform(products, clickNumMap);
        }
        return ProductListClientResp.builder()
                .loanProductList(rtnVo)
                .guideWords(guideWords)
                .build();
    }

    private List<Integer> getProducts(){
        String uid = ParameterThreadLocal.getUid();
        String resp = redisClient.getRedisTemplate().opsForValue().get(uid);
        if(StringUtils.isEmpty(resp)){
            return null;
        }
        return  JSON.parseArray(resp, Integer.class);

    }

    private boolean isOutOfTime(){
        String uid = ParameterThreadLocal.getUid();
        if(StringUtils.isEmpty(uid)){
            return true;
        }
        Integer singleOutOfTime = configs.getDefaultOutOfTime();
        Date date = new DateTime().minusHours(singleOutOfTime).toDate();

        ProductClickLogExample productClickLogExample = new ProductClickLogExample();
        productClickLogExample.createCriteria().andUidEqualTo(uid).andUpdatedAtGreaterThanOrEqualTo(date);

        List<ProductClickLog> productClickLogs = productClickLogMapper.selectByExample(productClickLogExample);
        return CollectionUtils.isEmpty(productClickLogs);
    }

    private boolean getCustomProp(){
        List<String> userRecommentProp = configs.getUserRecommentProp();
        String uid = ParameterThreadLocal.getUid();
        if(StringUtils.isEmpty(uid)){
            return false;
        }
        String mobile = getMobileNoCheck(uid);
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        String endOfMobile = mobile.substring(mobile.length() - 1, mobile.length());
        return userRecommentProp.contains(endOfMobile);
    }

    private List<Integer> getClickProductIds(){
        Integer singleOutOfTime = configs.getDefaultOutOfTime();
        Date date = new DateTime().minusHours(singleOutOfTime).toDate();
        String uid = ParameterThreadLocal.getUid();
        if(StringUtils.isEmpty(uid)){
            return Lists.newArrayList();
        }
        ProductClickLogExample productClickLogExample = new ProductClickLogExample();
        productClickLogExample.createCriteria().andUpdatedAtLessThanOrEqualTo(date).andUidEqualTo(uid);
        productClickLogExample.setOrderByClause("updated_at asc");
        List<ProductClickLog> productClickLogs = productClickLogMapper.selectByExample(productClickLogExample);
        return Lists.newArrayList(Collections2.transform(productClickLogs, new Function<ProductClickLog, Integer>() {
            @Override
            public Integer apply(ProductClickLog input) {
                return input.getProductId();
            }
        }));
    }

    private Map<String, Integer> getClickNumMap() {
        Map<String, Integer> productUserNum = Maps.newHashMap();
        if (1 == configs.getClickNumSwitch()) {
            productUserNum = configs.getProductClickNum();
        }
        List<ProductClickNumBo> productClickNum = getProductClickNum();
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

    private List<Product> getProducts(ProductListClientReq productListClientReq) {
        //没有筛选条件的默认排序
        if((StringUtils.isEmpty(productListClientReq.getSortValue()) || ProductSortEnum.DEFAULT.getValue().equals(productListClientReq.getSortValue())) && (null == productListClientReq.getFilterType() || StringUtils.isEmpty(productListClientReq.getFilterValue()))){
            //根据用户点击次数以及20%（默认20%，可配置）用户的个性化排序
            return customProducts();
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS);
        if (StringUtils.isEmpty(productListClientReq.getSortValue()) || ProductSortEnum.DEFAULT.getValue().equals(productListClientReq.getSortValue())) {
            productExample.setOrderByClause("rank asc");
        } else if (ProductSortEnum.LEND_FAST.getValue().equals(productListClientReq.getSortValue())) {
            productExample.setOrderByClause("lend_time asc");
        } else if (ProductSortEnum.FEE_RATE_LOW.getValue().equals(productListClientReq.getSortValue())) {
            productExample.setOrderByClause("annual_rate asc");
        } else if (ProductSortEnum.PASS_RATE_HIGH.getValue().equals(productListClientReq.getSortValue())) {
            productExample.setOrderByClause("pass_rate desc");
        }
        if (null == productListClientReq.getFilterType() || StringUtils.isEmpty(productListClientReq.getFilterValue())) {
            return productMapper.selectByExample(productExample);
        }
        if (ProductFilterTypeEnum.LEND_AMOUNT == productListClientReq.getFilterType()) {
            String[] filterAmount = StringUtils.split(productListClientReq.getFilterValue(), ",");
            Integer filterMinAmount = Integer.parseInt(filterAmount[0]);
            Integer filterMaxAmount = Integer.parseInt(filterAmount[1]);

            criteria.andMinAmountLessThanOrEqualTo(filterMaxAmount).andMaxAmountGreaterThanOrEqualTo(filterMinAmount);
            return productMapper.selectByExample(productExample);

        } else if (ProductFilterTypeEnum.PRODUCT_TAGS == productListClientReq.getFilterType()) {

            criteria.andTagsLike("%" + productListClientReq.getFilterValue() + "%");
            return productMapper.selectByExample(productExample);
        }

        return null;
    }

    private List<Product> customProducts(){
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS);
        //配置一定量的用户使用个性化推荐（默认20%）
        boolean isCustom = getCustomProp();
        if (!isOutOfTime() && isCustom) {
            List<Integer> ids = getProducts();
            if(CollectionUtils.isNotEmpty(ids)){
                return getClickProducts(ids);
            }
        }
        List<Integer> clickProductIds = Lists.newArrayList();
        if (isCustom) {
            clickProductIds = getClickProductIds();
        }
        productExample.setOrderByClause("rank asc");
        String uid = ParameterThreadLocal.getUid();
        List<Product> defaultProducts = productMapper.selectByExample(productExample);
        if(CollectionUtils.isEmpty(clickProductIds)){
            if(StringUtils.isNotEmpty(uid)){
                List<Integer> ids = Lists.newArrayList(Collections2.transform(defaultProducts, new Function<Product, Integer>() {
                    @Override
                    public Integer apply(Product input) {
                        return input.getId();
                    }
                }));
                redisClient.getRedisTemplate().opsForValue().set(uid, JSON.toJSONString(ids));
            }
            return defaultProducts;
        }
        criteria.andIdNotIn(clickProductIds);
        List<Product> clickProducts = getClickProducts(clickProductIds);
        List<Product> noClickProducts = delClickProducts(defaultProducts, clickProducts);
        noClickProducts.addAll(clickProducts);
        if(StringUtils.isNotEmpty(uid) && CollectionUtils.isNotEmpty(noClickProducts)){
            List<Integer> ids = Lists.newArrayList(Collections2.transform(noClickProducts, new Function<Product, Integer>() {
                @Override
                public Integer apply(Product input) {
                    return input.getId();
                }
            }));
            redisClient.getRedisTemplate().opsForValue().set(uid, JSON.toJSONString(ids));
        }
        return noClickProducts;
    }

    private List<Product> delClickProducts(List<Product> defaultProducts, List<Product> clickProducts){
        List<Product> noClickProducts = Lists.newArrayList();
        for (Product product: defaultProducts) {
            if(!clickProducts.contains(product)){
                noClickProducts.add(product);
            }
        }
        return noClickProducts;
    }

    private List<Product> getClickProducts(List<Integer> clickProductIds){
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andStatusEqualTo(CredittoolsConstants.ONLINE_STATUS).andIdIn(clickProductIds);
        productExample.setOrderByClause("field(id,"+ Joiner.on(",").join(clickProductIds) +" )");
        return productMapper.selectByExample(productExample);
    }

    private List<ProductClientResp> transform(List<Product> loanProducts, Map<String, Integer> clickNum) {
        String credittoolsUrl = configs.getCredittoolsUrl();
        List<ProductClientResp> products = Lists.newArrayList();
        for (Product product : loanProducts) {
            products.add(ProductClientResp.builder()
                    .feeRate(product.getShowFeeRate() ? product.getFeeRate() : null)
                    .iconUrl(product.getIconUrl())
                    .id(product.getProductId())
                    //拼接跳转的url，id定位贷款产品，p_u定位用户, r_c定位是否为推广页面
                    .jumpUrl( credittoolsUrl + REDIRECT_URL + "?id=" + product.getId() + (ParameterThreadLocal.getUid() == null ? "" : "&p_u=" + ParameterThreadLocal.getUid()) + (CredittoolsConstants.HB_CHANNEL.equals(ParameterThreadLocal.getRequestChannel()) ? "&r_c=" + CredittoolsConstants.HB_CHANNEL : ""))
                    .mark(product.getMark())
                    .name(product.getName())
                    .clickNum(formatClickNumStr(clickNum.get(product.getProductId())))
                    .promotion(product.getPromotion())
                    .optionalInfo(Lists.newArrayList(Collections2.transform(buildOptionalInfo(product), new Function<LoanProductResp.OptionalInfo, ProductClientResp.OptionalInfo>() {
                        @Override
                        public ProductClientResp.OptionalInfo apply(LoanProductResp.OptionalInfo input) {
                            return ProductClientResp.OptionalInfo.builder().type(input.getType()).value(input.getValue()).build();
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
        if (num >= 10000) {
            df.setRoundingMode(RoundingMode.FLOOR);
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

    public void unionLogin(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("p_u");
        String key = request.getParameter("key");
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

    public String checkUser(Lattery9188CheckUserReq req) {
        if (req == null || StringUtils.isBlank(req.getUser_id()))
            return CredittoolsConstants.LOTTERY9188_CHECKUSER_UNLOGIN;

        UserVo userVo = userRpc.getUserInfo(req.getUser_id());
        if (userVo == null)
            return CredittoolsConstants.LOTTERY9188_CHECKUSER_UNLOGIN;

        return CredittoolsConstants.LOTTERY9188_CHECKUSER_LOGIN;
    }

    public void redirectLoanProductUrl(HttpServletRequest request, HttpServletResponse response){
        String uid = request.getParameter("p_u");
        if(StringUtils.isEmpty(uid)){
            uid = "not login user";
        }
        String id = request.getParameter("id");
        // 非客户端进入的贷款产品连接不记录点击次数
        String requestChannel = request.getParameter("r_c");
        if(CredittoolsConstants.HB_CHANNEL.equals(requestChannel)){
            taskExecutor.execute(new ClickTimeInc(uid, id));
        }
        try {
            response.sendRedirect(getJumpUrl(Integer.valueOf(id), request.getParameter("p_u")));
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

    private String getJumpUrl(Integer id, String uid){
        Product product = productMapper.selectByPrimaryKey(id);
        if(product == null){
            throw new CjjClientException(ErrorResponseConstants.PRODUCT_NOT_FOUND_CODE, ErrorResponseConstants.PRODUCT_NOT_FOUND_MSG);
        }
        if(configs.getUnionLoginProducts().contains(product.getProductId()) && StringUtils.isNotEmpty(uid)){
            return  product.getJumpUrl() + "&p_u=" + uid;
        }
        return product.getJumpUrl();
    }

}
