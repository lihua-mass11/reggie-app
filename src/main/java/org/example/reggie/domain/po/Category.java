package org.example.reggie.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("菜品分类接口")
public class Category {

    @TableId("id")
    private Long id;

    @ApiModelProperty("员工id")
    private Long employeeId;

    @ApiModelProperty("类型 1 菜品分类 2 套餐分类")
    private Integer type;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("顺序")
    private Integer sort;

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
