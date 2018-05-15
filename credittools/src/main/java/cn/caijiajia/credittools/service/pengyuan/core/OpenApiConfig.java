package cn.caijiajia.credittools.service.pengyuan.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiConfig {
    private String hostName;
    private String version;
    private String appId;
}
