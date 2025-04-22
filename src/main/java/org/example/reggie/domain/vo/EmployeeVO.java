package org.example.reggie.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("员工表返回响应VO")
public class EmployeeVO {

    @ApiModelProperty("员工id")
    private Long id;

    @ApiModelProperty("员工职位")
    private String name;

    @ApiModelProperty("员工名称")
    private String username;

    @ApiModelProperty("员工密码")
    private String password;

    @ApiModelProperty("员工电话号码")
    private String phone;

    @ApiModelProperty("员工性别")
    private Integer sex;

    @ApiModelProperty("员工身份证")
    private String idNumber;

    @ApiModelProperty("员工状态")
    private Integer status;

}
