package org.example.reggie.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 唐三
 * description: 结果统一返回实体,用于响应统一,
 *  T为泛型
 */
@Api("结果统一返回实体")
@Data
public class ResultViewEntity<T> {

    @ApiModelProperty("状态码 0代表失败 1代表正常")
    private Integer code;

    @ApiModelProperty("异常信息返回")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("动态库返回")
    private Map map = new HashMap();

    /**
     * 响应成功返回
     * @param data
     * @return
     */
    public static <T> ResultViewEntity<T> successful(T data) {
        ResultViewEntity<T> resultViewEntity = new ResultViewEntity<>();
        resultViewEntity.code = 1;
        resultViewEntity.data = data;
        return resultViewEntity;
    }

    /**
     * 响应失败返回
     * @param msg
     * @return
     */
    public static <T> ResultViewEntity<T> error(String msg) {
        ResultViewEntity<T> resultViewEntity = new ResultViewEntity<>();
        resultViewEntity.code = 0;
        resultViewEntity.msg = msg;
        return resultViewEntity;
    }

    /**
     * 动态数据存储
     * @param key
     * @param value
     * @return
     */
    public static <T> ResultViewEntity<T> add(String key, Object value) {
        ResultViewEntity<T> resultViewEntity = new ResultViewEntity<>();
        resultViewEntity.map.put(key,value);
        return resultViewEntity;
    }
}
