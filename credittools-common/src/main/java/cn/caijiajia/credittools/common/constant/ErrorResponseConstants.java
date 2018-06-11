/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.common.constant;

/**
 * Created by liujianyang on 2018/4/25.
 */
public class ErrorResponseConstants {

    public static final int FIELD_LENGTH_EXCESS_CODE=6000;
    public static final String FIELD_LENGTH_EXCESS_MSG="产品名称字段长度超过指定长度（指定长度7）";
    public static final String MARK_FIELD_LENGTH_EXCESS_MSG="角标字段长度超过指定长度（指定长度7）";

    public static final int PRODUCT_NOT_FOUND_CODE=6001;
    public static final String PRODUCT_NOT_FOUND_MSG="没有找到产品";
    public static final Integer GET_PRODUCT_NOT_EXIST_CODE = 0001;
    public static final String GET_PRODUCT_NOT_EXIST_MSG = "不存在该贷款产品";

    public static final Integer CHANGE_PRODUCT_RANK_FAILED_CODE = 0002;
    public static final String CHANGE_PRODUCT_RANK_FAILED_MSG = "更改产品位置序号失败";

    public static final Integer CHANGED_RANK_OVERFLOW_CODE = 0003;
    public static final String CHANGED_RANK_OVERFLOW_MSG = "输入产品位置序号大于当前总记录条数";

    public static final Integer CHANGE_PRODUCT_STATUS_FAILED_CODE = 0004;
    public static final String CHANGE_PRODUCT_STATUS_FAILED_MSG = "更改产品上下线状态失败";

    public static final int ERR_RESX_UPLOAD_FAILURE_CODE = 9458;
    public static final String ERR_RESX_UPLOAD_FAILURE_MSG = "资源上传失败！";

    public static final int USER_NOT_EXISTS_CODE = 8901;
    public static final String USER_NOT_EXISTS_MESSAGE = "用户不存在";

    public static final int USER_PHONE_NOT_EXISTS_CODE = 8902;
    public static final String USER_PHONE_NOT_EXISTS_MESSAGE = "用户手机号不存在";

    public static final int GET_UNION_URL_ERR_CODE = 6002;
    public static final String GET_UNION_URL_ERR_MSG = "未配置联合登录url";

    public static final int REDIRECT_FAILED_CODE = 6039;
    public static final String REDIRECT_FAILED_MSG = "重定向失败";

    public static final String LOTTERY9188_CHECKUSER_REQPARAM_ERROR_MSG = "参数错误：user_id为空或不存在";

    public static final int REQUEST_TOKEN_FAIL_CODE = 7000;
    public static final String REQUEST_TOKEN_FAIL_MSG = "点融魔借授权token接口异常";

}