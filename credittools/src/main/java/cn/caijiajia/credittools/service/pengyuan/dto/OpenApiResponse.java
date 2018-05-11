package cn.caijiajia.credittools.service.pengyuan.dto;

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
public class OpenApiResponse implements Serializable {
    private static final long serialVersionUID = 8595221368154428470L;
    private String code;
    private String message;
    private String data;
    private String timestamp;
    private String sign;
    private String randomKey;

}
