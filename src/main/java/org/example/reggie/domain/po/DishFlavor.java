package org.example.reggie.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("菜品口味实体")
public class DishFlavor {

    @TableId("id")
    @ApiModelProperty("菜品口味Id")
    private Long id;

    @ApiModelProperty("菜品id")
    private Long dishId;

    @ApiModelProperty("口味名称")
    private String name;

    @ApiModelProperty("口味种类集合")
    private String value;

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
