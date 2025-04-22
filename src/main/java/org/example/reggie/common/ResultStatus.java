package org.example.reggie.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * @author 唐三
 * scription: 异常结果状态
 */
@ApiModel("常见状态码")
public class ResultStatus {

    //常见异常
    @ApiModelProperty("算数异常")
    public static final Integer ARITHMETIC_ERROR = 11001;

    @ApiModelProperty("索引越界异常")
    public static final Integer INDEX_ROUND_ERROR = 11002;

    @ApiModelProperty("空指异常")
    public static final Integer Empty_VAR_ERROR = 11003;

    @ApiModelProperty("IO异常")
    public static final Integer IO_ERROR = 11004;

    @ApiModelProperty("数据库异常")
    public static final Integer MYSQL_ERROR = 11005;

    @ApiModelProperty("数据添加异常")
    public static final Integer MYSQL_SAVE_ERROR = 11006;

    @ApiModelProperty("数据更新异常")
    public static final Integer MYSQL_UPDATE_ERROR = 11007;

    @ApiModelProperty("数据删除异常")
    public static final Integer MYSQL_DELETE_ERROR = 11009;

    @ApiModelProperty("数据查询异常")
    public static final Integer MYSQL_SELECT_ERROR = 11010;

    //关于用户的异常
    @ApiModelProperty("用户密码错误")
    public static final Integer USER_PASSWORD_ERROR = 21001;

    @ApiModelProperty("用户名称有误")
    public static final Integer USER_NAME_ERROR = 21002;

    @ApiModelProperty("用户|菜品|... 状态异常")
    public static final Integer COLL_STATUS_ERROR = 21003;

    @ApiModelProperty("用户手机号有误")
    public static final Integer PHONE_ERROR = 21004;

    @ApiModelProperty("用户身份信息异常")
    public static final Integer USER_NUMBER_ERROR = 21005;

    //服务器异常
    @ApiModelProperty("服务器内部异常")
    public static final Integer ENGINE_ERROR = 10500;

    @ApiModelProperty("无效网关")
    public static final Integer WEBMASTER_ERROR = 10502;

    @ApiModelProperty("网关超时")
    public static final Integer WEBMASTER_TIME_ERROR = 10504;

    //网页异常
    @ApiModelProperty("请求格式有误")
    public static final Integer REQUEST_FORMAT_ERROR = 10400;

    @ApiModelProperty("网页不存在")
    public static final Integer REQUEST_VIEW_NOT_ERROR = 10404;

    @ApiModelProperty("权限不足")
    public static final Integer REQUEST_SECURITY_ERROR = 10403;

    @ApiModelProperty("不允许次请求 post | get ...")
    public static final Integer REQUEST_NOT_ERROR = 10405;
}
