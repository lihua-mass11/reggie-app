package org.example.reggie.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("employee")
@ApiModel("员工实体")
public class Employee implements Serializable {

    @TableId("id")
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

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //fill进行填充  FieldFill.INSERT填充策略,
    @ApiModelProperty("那个用户所创建")
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    //插入和更新时填充
    @ApiModelProperty("那个用户对其更新")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

}
