package org.example.reggie.utils.optimizes;

//if优化
public interface ExceptionOptimize {

    /**
     * 需要加工的值: 状态码 ->(加工) 异常值
     * @return
     */
    String returnException(Integer code);
}
