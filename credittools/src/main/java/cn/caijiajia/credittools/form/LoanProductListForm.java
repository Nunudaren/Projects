package cn.caijiajia.credittools.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductListForm {
    private String productId;
    private String productName;
    private String status;//上线/下线
    private Integer pageNo;
    private Integer pageSize;
}
