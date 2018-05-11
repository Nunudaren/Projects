package cn.caijiajia.credittools.pengyuan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiRequest implements Serializable {
    private static final long serialVersionUID = -865395666284608285L;
    private String charset;
    private String signType;
    private String format;
    private String timestamp;
    private String sign;
    private String randomKey;
    private String bizContent;
}
