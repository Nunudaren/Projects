package cn.caijiajia.credittools.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtil {

    private static final int MULTIPLE = 100;
    private static final String INTEGER_FORMAT = ",###";
    private static final String DOUBLE_FORMAT = ",###.##";

    private static DecimalFormat df = new DecimalFormat(",##0.00");


    /**
     * 元字符串转成Integer类型
     * 单位分
     * "10.00" 转成 1000分
     * "10" 转成 1000分
     */
    public static Integer yuanStr2FenInteger(String yuan) {
        if (yuan == null) {
            return null;
        }
        if (yuan.contains(".")) {
            BigDecimal dy = new BigDecimal(yuan);
            return dy.multiply(new BigDecimal(100)).intValue();
        }
        return Integer.parseInt(yuan) * 100;
    }

    public static Integer yuan2Fen(Integer yuan) {
        if (yuan == null) {
            return null;
        }
        BigDecimal dy = new BigDecimal(yuan);
        return dy.multiply(new BigDecimal(MULTIPLE)).intValue();
    }

    public static Integer fen2Yuan(Integer fen) {
        if (fen == null) {
            return null;
        }
        BigDecimal dy = new BigDecimal(fen);
        return dy.divide(new BigDecimal(MULTIPLE)).intValue();
    }

    public static String fen2YuanWithFormat(Integer fen) {
        return decimalFormat(fen2Yuan(fen));
    }

    /**
     * 格式化，添加千分隔符
     * @param money
     * @return
     */
    public static String decimalFormat(Integer money){
        if(money != null){
            DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
            decimalFormat.applyPattern(INTEGER_FORMAT);
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            return  decimalFormat.format(money);
        }
        return "";
    }

    public static BigDecimal fenInteger2YuanBigDecimal(Integer fen){
        if(fen == null){
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return new BigDecimal(df.format(Double.parseDouble(fen.toString()) / 100));
    }

    public static String decimalFormatFromFen(Integer fen){
        BigDecimal yuan = fenInteger2YuanBigDecimal(fen);
        return df.format(yuan);
    }


    /**
     * 格式化，添加千分隔符
     * @param money
     * @return
     */
    public static String decimalFormatWithYuan(Double money){
        if(money != null){
            DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
            decimalFormat.applyPattern(DOUBLE_FORMAT);
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            return  decimalFormat.format(money);
        }
        return "";
    }

    /**
     * 格式化，添加千分隔符
     * @return
     */
    public static String decimalFormatWithFen(Integer fen){
        fen = fen2Yuan(fen);
        if(fen != null){
            DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
            decimalFormat.applyPattern(DOUBLE_FORMAT);
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            return  decimalFormat.format(fen);
        }
        return "";
    }

    /**
     *取整元，角和分位置0
     * @param fen
     * @return
     */
    public static Integer floorYuan(Integer fen){
        if (null == fen){
            return null;
        }
        if ( 100 > fen){
            return 0;
        }
        return Integer.valueOf(fen.toString().substring(0,fen.toString().length()-2))*100;
    }

}