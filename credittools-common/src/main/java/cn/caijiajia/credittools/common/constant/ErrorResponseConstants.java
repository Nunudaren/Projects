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
    public static final String FIELD_LENGTH_EXCESS_MSG="产品名称字段或者角标字段长度超过指定长度（指定长度%s）";

    public static final int PRODUCT_NOT_FOUND_CODE=6001;
    public static final String PRODUCT_NOT_FOUND_MSG="没有找到产品";
    public static final Integer GET_PRODUCT_NOT_EXIST_CODE = 0001;
    public static final String GET_PRODUCT_NOT_EXIST_MSG = "不存在该贷款产品";

    public static final Integer CHANGE_PRODUCT_RANK_FAILED_CODE = 0002;
    public static final String CHANGE_PRODUCT_RANK_FAILED_MSG = "更改产品位置序号失败";

    public static final Integer CHANGE_PRODUCT_STATUS_FAILED_CODE = 0003;
    public static final String CHANGE_PRODUCT_STATUS_FAILED_MSG = "更改产品上下线状态失败";

    public static final int ERR_RESX_UPLOAD_FAILURE_CODE = 9458;
    public static final String ERR_RESX_UPLOAD_FAILURE_MSG = "资源上传失败！";

}