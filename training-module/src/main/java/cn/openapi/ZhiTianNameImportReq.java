package cn.openapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author winter
 * @date 2019-01-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZhiTianNameImportReq {

    /**
     * 渠道
     */
    private String channel;

    /**
     * 名单数量
     */
    private int dataLength;

    /**
     * 批量名单
     */
    private List<DataInfo> dataList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataInfo {

        /**
         * 基本信息
         */
        private BaseInfo baseInfo;

        /**
         * 附加信息
         */
        private AdditionalInfo additionalInfo;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class BaseInfo {

            /**
             * 数据来源，不同数据源对应不同的编码
             */
            @NotNull(message = "custSrc 不能为 null")
            private String custSrc;

            /**
             * 姓名
             */
            @NotNull(message = "name 不能为 null")
            private String name;

            /**
             * 性别
             * 1：男，2：女
             */
            @NotNull(message = "gender 不能为 null")
            private Integer gender;

            /**
             * 出生日期
             * 格式： yyyyMMdd，如：19980915
             */
            private String birthDate;

            /**
             * 手机号
             */
            @NotNull(message = "teleNum 不能为 null")
            private String teleNum;

            /**
             * 邮箱
             */
            private String email;

            /**
             * 学历
             * 1：小学，2：初中，3：高中，4：中专，5：大专，6：本科，7：硕士，8：博士
             */
            private Integer education;

            /**
             * 省份
             */
            @NotNull(message = "province 不能为 null")
            private String province;

            /**
             * 城市
             */
            @NotNull(message = "city 不能为 null")
            private String city;

            /**
             * 是否有信用卡
             * 1：有，0：否
             */
            @NotNull(message = "hasCreditCard 不能为 null")
            private Integer hasCreditCard;

        }


        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AdditionalInfo {

            /**
             * 信用卡额度
             * 单位：元
             */
            private Integer credit;

            /**
             * 拥有信用卡的年限
             * 单位：年
             */
            private Integer creditYears;

            /**
             * 是否曾有过的贷款经历
             * 1：有，0：否
             */
            private Integer hasLoaned;

            /**
             * 是否有房贷
             * 1：有，0：否
             */
            private Integer hasMortgage;

            /**
             * 是否有车贷
             * 1：有，0：否
             */
            private Integer hasCarLoan;

            /**
             * 职业类型
             * 1：公司职员；2：私营业主；3：公务员；4-其他
             */
            private Integer profession;

            /**
             * 税后月收入
             * 不含小数点的数字，单位：元
             */
            private Long income;

            /**
             * 发薪方式
             * 1：银行转账发薪；2：通过现金发薪
             */
            private Integer payoffType;

            /**
             * 是否单身
             * 1：有，0：否
             */
            private Integer isSingle;

        }
    }
}