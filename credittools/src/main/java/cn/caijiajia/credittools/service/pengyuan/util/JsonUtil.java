package cn.caijiajia.credittools.service.pengyuan.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonUtil() {
    }

    public static <T> T read(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception var3) {
            log.error("read json to class error", var3);
            throw new RuntimeException(var3.getMessage());
        }
    }

    public static <T> T readGeneric(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception var3) {
            log.error("read json to typeReference error", var3);
            throw new RuntimeException(var3.getMessage());
        }
    }

    public static String write(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception var2) {
            log.error("write json error", var2);
            throw new RuntimeException(var2.getMessage());
        }
    }

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
