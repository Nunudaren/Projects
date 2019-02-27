/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Harry on 2018/1/19.
 */
public class PriceFormatUtils {

    private static final String DECIMAL_FORMAT = "0.00";

    private static final BigDecimal PENNY_DIVIDE_UNIT = new BigDecimal("100");

    public static String getStringFormatValue(Object value, boolean isPenny, String pattern){

        String formatValue = null;

        if(value != null){
            BigDecimal decimalValue = new BigDecimal(String.valueOf(value));

            if(isPenny){
                decimalValue = decimalValue.divide(PENNY_DIVIDE_UNIT);
            }

            DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
            decimalFormat.applyPattern(pattern);

            formatValue = decimalFormat.format(decimalValue);
        }

        return formatValue;
    }

    public static String getStringFormatValue(Object value, boolean isPenny){

        return getStringFormatValue(value, isPenny, DECIMAL_FORMAT);
    }

    public static Integer getIntegerFormatValue(Object value, boolean isPenny) {

        Integer formatValue = null;

        if (value != null) {
            BigDecimal decimalValue = new BigDecimal(String.valueOf(value));

            if(!isPenny){
                decimalValue = decimalValue.multiply(PENNY_DIVIDE_UNIT);
            }

            formatValue = decimalValue.intValue();
        }

        return formatValue;
    }
}
