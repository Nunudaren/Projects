package cn.caijiajia.credittools.service;

import cn.caijiajia.credittools.bo.LoanProductBo;
import cn.caijiajia.credittools.configuration.Configs;
import cn.caijiajia.credittools.constant.ProductFilterTypeEnum;
import cn.caijiajia.credittools.constant.ProductSortEnum;
import cn.caijiajia.credittools.form.ProductListClientForm;
import cn.caijiajia.credittools.utils.DateUtil;
import cn.caijiajia.credittools.utils.MoneyUtil;
import cn.caijiajia.credittools.vo.LoanProductFilterVo;
import cn.caijiajia.credittools.vo.ProductClientVo;
import cn.caijiajia.credittools.vo.ProductListClientVo;
import cn.caijiajia.loanproduct.common.Resp.LoanProductResp;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangyang on 2017/11/9.
 */

/**
 * 贷款产品聚合页Service
 *
 */
@Service
public class LoanProductsService {

    @Resource
    private Configs configs;

    public LoanProductFilterVo getLoanProductFilter() {
        LoanProductFilterVo rtnVo = new LoanProductFilterVo();
        if (null != configs.getLoanProductFilters()) {
            rtnVo = configs.getLoanProductFilters();
        }
        return rtnVo;
    }

    public ProductListClientVo getLoanProductList(ProductListClientForm loanProductListForm) {
        List<LoanProductBo> loanProducts = Lists.newArrayList();
        String guideWords = null;
        if (null != configs.getLoanProducts()) {
            loanProducts = configs.getLoanProducts();
        }
        if (null != configs.getGuideWords()) {
            guideWords = configs.getGuideWords();
        }
        loanProducts = filtering(loanProducts, loanProductListForm.getFilterType(), loanProductListForm.getFilterValue());
        loanProducts = sort(loanProducts, loanProductListForm.getSortValue());
        List<ProductClientVo> rtnVo = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(loanProducts)) {
            rtnVo = transform(loanProducts);
        }
        return ProductListClientVo.builder()
                .loanProductList(rtnVo)
                .guideWords(guideWords)
                .build();
    }

    private List<LoanProductBo> filtering(List<LoanProductBo> loanProducts, ProductFilterTypeEnum filterType, String filterValue) {
        if (CollectionUtils.isEmpty(loanProducts)) {
            return loanProducts;
        }
        List<LoanProductBo> rtnList = Lists.newArrayList();
        if (null == filterType || StringUtils.isEmpty(filterValue)) {
            for (LoanProductBo loanProduct : loanProducts) {
                if (DateUtil.convert2Date(loanProduct.getOnlineTime(), "yyyy-MM-dd hh:mm:ss").before(new Date())
                        && DateUtil.convert2Date(loanProduct.getOfflineTime(), "yyyy-MM-dd hh:mm:ss").after(new Date())) {
                    rtnList.add(loanProduct);
                }
            }
        }
        if (ProductFilterTypeEnum.LEND_AMOUNT == filterType) {
            for (LoanProductBo loanProduct : loanProducts) {
                String[] filterAmount = StringUtils.split(filterValue, ",");
                Integer filterMinAmount = Integer.parseInt(filterAmount[0]);
                Integer filterMaxAmount = Integer.parseInt(filterAmount[1]);
                if (loanProduct.getMaxAmount() >= filterMinAmount && loanProduct.getMinAmount() <= filterMaxAmount) {
                    if (DateUtil.convert2Date(loanProduct.getOnlineTime(), "yyyy-MM-dd hh:mm:ss").before(new Date())
                            && DateUtil.convert2Date(loanProduct.getOfflineTime(), "yyyy-MM-dd hh:mm:ss").after(new Date())) {
                        rtnList.add(loanProduct);
                    }
                }
            }
        } else if (ProductFilterTypeEnum.PRODUCT_TAGS == filterType) {
            for (LoanProductBo loanProduct : loanProducts) {
                if (loanProduct.getTags().contains(filterValue)) {
                    if (DateUtil.convert2Date(loanProduct.getOnlineTime(), "yyyy-MM-dd hh:mm:ss").before(new Date())
                            && DateUtil.convert2Date(loanProduct.getOfflineTime(), "yyyy-MM-dd hh:mm:ss").after(new Date())) {
                        rtnList.add(loanProduct);
                    }
                }
            }
        }
        return rtnList;
    }

    private List<LoanProductBo> sort(List<LoanProductBo> loanProducts, String sortValue) {
        if (CollectionUtils.isEmpty(loanProducts)) {
            return loanProducts;
        }
        if (StringUtils.isEmpty(sortValue) || ProductSortEnum.DEFAULT.getValue().equals(sortValue)) {
            return sortByRank(loanProducts);
        }
        if (ProductSortEnum.FEE_RATE_LOW.getValue().equals(sortValue)) {
            return sortByAnnualRate(loanProducts);
        }
        if (ProductSortEnum.LEND_FAST.getValue().equals(sortValue)) {
            return sortByLendSpeed(loanProducts);
        }
        if (ProductSortEnum.PASS_RATE_HIGH.getValue().equals(sortValue)) {
            return sortByPassRate(loanProducts);
        } else {
            return loanProducts;
        }
    }

    private List<LoanProductBo> sortByRank(List<LoanProductBo> loanProducts) {
        Ordering<LoanProductBo> byRank = new Ordering<LoanProductBo>() {
            @Override
            public int compare(LoanProductBo left, LoanProductBo right) {
                return left.getRank() - right.getRank();
            }
        };
        return byRank.sortedCopy(loanProducts);
    }

    private List<LoanProductBo> sortByAnnualRate(List<LoanProductBo> loanProducts) {
        Ordering<LoanProductBo> byAnnualRate = new Ordering<LoanProductBo>() {
            @Override
            public int compare(LoanProductBo left, LoanProductBo right) {
                return left.getAnnualRate().compareTo(right.getAnnualRate());
            }
        };
        return byAnnualRate.sortedCopy(loanProducts);
    }

    private List<LoanProductBo> sortByLendSpeed(List<LoanProductBo> loanProducts) {
        Ordering<LoanProductBo> byLendSpeed = new Ordering<LoanProductBo>() {
            @Override
            public int compare(LoanProductBo left, LoanProductBo right) {
                return left.getLendTime() - right.getLendTime();
            }
        };
        return byLendSpeed.sortedCopy(loanProducts);
    }

    private List<LoanProductBo> sortByPassRate(List<LoanProductBo> loanProducts) {
        Ordering<LoanProductBo> byPassRate = new Ordering<LoanProductBo>() {
            @Override
            public int compare(LoanProductBo left, LoanProductBo right) {
                return right.getPassRate() - left.getPassRate();
            }
        };
        return byPassRate.sortedCopy(loanProducts);
    }

    private List<ProductClientVo> transform(final List<LoanProductBo> loanProducts) {
        return Lists.newArrayList(Collections2.transform(loanProducts, new Function<LoanProductBo, ProductClientVo>() {
            @Override
            public ProductClientVo apply(LoanProductBo input) {
                return ProductClientVo.builder()
                        .feeRate(input.isShowFeeRate() ? input.getFeeRate() : null)
                        .iconUrl(input.getIconUrl())
                        .id(input.getId())
                        .jumpUrl(input.getJumpUrl())
                        .mark(input.getMark())
                        .name(input.getName())
                        .promotion(input.getPromotion())
                        .optionalInfo(Lists.newArrayList(Collections2.transform(buildOptionalInfo(input), new Function<LoanProductResp.OptionalInfo, ProductClientVo.OptionalInfo>() {
                            @Override
                            public ProductClientVo.OptionalInfo apply(LoanProductResp.OptionalInfo input) {
                                return ProductClientVo.OptionalInfo.builder().type(input.getType()).value(input.getValue()).build();
                            }
                        })))
                        .build();
            }
        }));
    }

    public LoanProductResp getLoanProduct(String id) {
        List<LoanProductBo> loanProducts = configs.getLoanProducts();
        for (LoanProductBo loanProduct : loanProducts) {
            if (loanProduct.getId().equals(id)) {
                if (DateUtil.convert2Date(loanProduct.getOnlineTime(), "yyyy-MM-dd hh:mm:ss").before(new Date())
                        && DateUtil.convert2Date(loanProduct.getOfflineTime(), "yyyy-MM-dd hh:mm:ss").after(new Date())) {
                    return LoanProductResp.builder()
                            .id(loanProduct.getId())
                            .name(loanProduct.getName())
                            .iconUrl(loanProduct.getIconUrl())
                            .mark(loanProduct.getMark())
                            .optionalInfo(buildOptionalInfo(loanProduct))
                            .promotion(loanProduct.getPromotion())
                            .jumpUrl(loanProduct.getJumpUrl())
                            .build();
                }
                return null;
            }
        }
        return null;
    }

    private List<LoanProductResp.OptionalInfo> buildOptionalInfo(LoanProductBo loanProduct) {
        List<LoanProductResp.OptionalInfo> optionalInfo = Lists.newArrayList();
        Integer minAmount = loanProduct.getMinAmount();
        Integer maxAmount = loanProduct.getMaxAmount();
        optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                .type("额度范围")
                //最低额度与最高额度相等时只显示一个额度值
                .value(minAmount.intValue() == maxAmount.intValue() ?
                        MoneyUtil.decimalFormat(maxAmount) + "元" :
                        MoneyUtil.decimalFormat(minAmount) + "-" + MoneyUtil.decimalFormat(maxAmount) + "元")
                .build());
        if (loanProduct.isShowFeeRate()) {
            optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                    .type("参考利率")
                    .value(loanProduct.getFeeRate())
                    .build());
        } else {
            optionalInfo.add(LoanProductResp.OptionalInfo.builder()
                    .type("借款周期")
                    .value(loanProduct.getLendPeriod())
                    .build());
        }
        if (!loanProduct.isAmountFirst()) {
            LoanProductResp.OptionalInfo temp = optionalInfo.get(0);
            optionalInfo.set(0, optionalInfo.get(1));
            optionalInfo.set(1, temp);
        }
        return optionalInfo;
    }
}
