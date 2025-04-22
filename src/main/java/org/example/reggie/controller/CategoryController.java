package org.example.reggie.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultViewEntity;
import org.example.reggie.domain.dto.CategoryDTO;
import org.example.reggie.domain.po.Category;
import org.example.reggie.domain.vo.CategoryVO;
import org.example.reggie.domain.vo.EmployeeVO;
import org.example.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("菜品分类表示层")
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("菜品种类分页几口")
    @GetMapping("/page")
    public ResultViewEntity<Page<CategoryVO>> pageCategory(
            @ApiParam("页数") @RequestParam Integer page,
            @ApiParam("每页条数") @RequestParam Integer pageSize,
            @ApiParam("搜索") @RequestParam(required = false)  String name
    ) {

        log.info("请求格式: {} ,地址: {}","GET","/category/page");
        log.info("页面:" + page + " ,条数:" + pageSize + " ,名称:" + name);

        //数据转换
        Page<Category> categoryPage =
                categoryService.pageServiceCategory(page,pageSize,name);

        Page<CategoryVO> categoryVOPage = new Page<>();
        BeanUtil.copyProperties(categoryPage, categoryVOPage);

        return ResultViewEntity.successful(categoryVOPage);
    }

    @ApiOperation("分类添加接口")
    @PostMapping
    public ResultViewEntity<String> saveCategoryDish(
            @ApiParam("分类实体") @RequestBody CategoryDTO categoryDTO
    ) {

        log.info("请求格式: {} ,地址: {}","POST","/category/");
        log.info("category: {}",categoryDTO);

        categoryService.saveServiceCategory(categoryDTO);

        return ResultViewEntity.successful("成功添加🙂‍↔️🙂‍↔️");
    }

    @ApiOperation("分类修改接口")
    @PutMapping
    public ResultViewEntity<String> editCategory(
            @ApiParam("修改实体") @RequestBody CategoryDTO categoryDTO
    ) {

        log.info("请求格式: {} ,地址: {}","PUT","/category/");
        log.info("category: {}",categoryDTO);

        categoryService.editServiceCategory(categoryDTO);

        return ResultViewEntity.successful("恭喜您,成功修改❤️❤️");
    }

    @ApiOperation("分类删除")
    @DeleteMapping
    public ResultViewEntity<String> deleteCategory(
            @ApiParam("分类id") String ids
    ) {

        log.info("请求格式: {} ,地址: {}","DELETE","/category/");
        System.out.println("ids:" + ids);

        categoryService.removeById(Long.parseLong(ids));

        return ResultViewEntity.successful("恭喜您成功删除😁😁");
    }
}
