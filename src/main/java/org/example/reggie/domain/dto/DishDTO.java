package org.example.reggie.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.example.reggie.domain.po.Dish;
import org.example.reggie.domain.po.DishFlavor;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("菜品输入DTO")
public class DishDTO {

    @TableId("id")
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品分类id")
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("0 停售 1 起售")
    private Integer status;

    @ApiModelProperty("菜品口味集合")
    private List<DishFlavor> flavors;

}
