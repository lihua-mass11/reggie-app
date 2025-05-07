package org.example.reggie.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultViewEntity;
import org.example.reggie.domain.dto.DishDTO;
import org.example.reggie.domain.po.Dish;
import org.example.reggie.domain.vo.DishVO;
import org.example.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("菜品表示层")
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @ApiOperation("菜品分页接口")
    @GetMapping("/page")
    public ResultViewEntity<Page<DishVO>> pageDish(
            @ApiParam("页数") @RequestParam Integer page,
            @ApiParam("每页条数") @RequestParam Integer pageSize,
            @ApiParam("搜索") @RequestParam(required = false)  String name
    ) {

        log.info("请求格式: {} ,地址: {}","GET","/dish/page");
        log.info("页面:" + page + " ,条数:" + pageSize + " ,名称:" + name);

        //返回
        return ResultViewEntity.successful(
                dishService.dishServicePage(page,pageSize,name)
        );
    }

    @ApiOperation("添加菜品接口")
    @PostMapping
    public ResultViewEntity<String> saveDish(
            @ApiParam("菜品") @RequestBody DishDTO dishDTO
     ) {

        log.info("请求格式: {} ,地址: {}","GET","/dish/page");
        log.info("页面dish: {}",dishDTO);

        dishService.saveServiceDish(dishDTO);

        return ResultViewEntity.successful("成功添加😋😋");
    }

}
