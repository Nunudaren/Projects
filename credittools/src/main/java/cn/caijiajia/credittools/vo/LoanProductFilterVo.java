package cn.caijiajia.credittools.vo;

import cn.caijiajia.credittools.bo.LoanProductFilterBo;
import cn.caijiajia.credittools.bo.LoanProductSortBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by wangyang on 2017/11/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductFilterVo {
    private String filterName;
    private List<LoanProductFilterBo> filters;
    private LoanProductSortBo sort;
}