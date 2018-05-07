package cn.caijiajia.credittools.bo;

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
public class LoanProductSortBo {
    private String name;
    private List<Option> options;

    @Data
    public static class Option {
        private String desp;
        private String value;
    }
}
