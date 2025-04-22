package org.example.reggie.utils;

import org.example.reggie.utils.optimizes.ExceptionOptimize;

/**
 * @author 唐三
 * description: 用于异常实体返回优化的工具类
 */
public class ExceptionOptimizeUtil {

    public static String returnException(Integer code, ExceptionOptimize optimize) {
        return optimize.returnException(code);
    }
}
