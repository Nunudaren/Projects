package cn.caijiajia.credittools.common.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author:chendongdong
 * @Date:2018/5/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PengyuanLoginReq {

    @NotBlank
    private String uuid;

    private String name;

    private String docNo; //身份证号
    @NotBlank
    private String mobile;

    private ExtendInfo extraField; //扩展字段

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtendInfo {

        private String confidence;  //置信度

        private String thresholdLevel; //阈值

        private String photoUrl; //活体照片

        private String policeUrl; //公安比对照片

        private String livingBodySource; //活体采集来源
    }
}
