package cn.caijiajia.loanmarket.common.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:chendongdong
 * @Date:2018/4/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YouyuUnionLoginResp {

    private String code;

    private String desc;

    private String cnickId;

    private String appId;

    private String accessToken;
}
