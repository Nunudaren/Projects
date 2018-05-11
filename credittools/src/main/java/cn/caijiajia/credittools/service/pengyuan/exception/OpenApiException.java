package cn.caijiajia.credittools.service.pengyuan.exception;

import lombok.Builder;
import lombok.Data;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
@Data
@Builder
public class OpenApiException extends Exception {
    private static final long serialVersionUID = 3480132143147947659L;
    private String message;
    private String code;

    public OpenApiException() {
    }

    public OpenApiException(String message) {
        this.message = message;
    }

    public OpenApiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }
}
