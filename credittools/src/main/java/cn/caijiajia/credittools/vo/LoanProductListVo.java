package cn.caijiajia.credittools.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:chendongdong
 * @Date:2018/5/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductListVo {
    private int rank;  //展示位置（对应客户端展示排序）
    private String productId;
    private String productName;
    private String iconUrl;
    private String onlineTime;
    private String offlineTime;
    private String status;//格式：上线/下线
}
