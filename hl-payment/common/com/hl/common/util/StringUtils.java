package com.hl.common.util;

/**
 * 功能/模块 ： 字符串相关公用函数
 * 
 * @author 黄艺勇 huangyy@adtec.com.cn
 * @version 修订历史： 日期 作者 参考 描述 北京先进数通版权所有.
 */

public class StringUtils extends org.apache.commons.lang.StringUtils {
    public static final int numMaxLength = 4;
    public static final String number = "0";


    /**
     * 将对象转化为string
     * @param obj
     * @return 如果对象为空则返回空字符串
     * @author
     */
    public static String obj2Str(Object obj) {
        if (obj == null) {
            return "";
        }

        return obj.toString();
    }

    

}
