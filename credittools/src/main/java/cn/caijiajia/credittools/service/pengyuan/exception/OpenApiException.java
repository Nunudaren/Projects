package cn.caijiajia.credittools.service.pengyuan.exception;

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
public class OpenApiException extends Exception {
    private static final long serialVersionUID = 3480132143147947659L;
    private String message;
    private String code;

    public OpenApiException(String message) {
        this.message = message;
    }

}
