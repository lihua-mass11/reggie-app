package org.example.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultStatus;
import org.example.reggie.domain.dto.CategoryDTO;
import org.example.reggie.domain.po.Category;
import org.example.reggie.exceptions.BusinessException;
import org.example.reggie.mapper.CategoryMapper;
import org.example.reggie.service.CategoryService;
import org.example.reggie.utils.BaseContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐三
 * description: 菜品分类业务逻辑处理层
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
                        implements CategoryService {

    @Override
    public Page<Category> pageServiceCategory(Integer page, Integer pageSize, String name) {
        log.info("菜品业务逻辑接口执行: {}","pageServiceCategory");
        Page<Category> categoryPage = new Page<>(page,pageSize);
        // 1,分页
        baseMapper.selectPage(
                categoryPage,
                new LambdaQueryWrapper<Category>().
                        like(ObjectUtil.isNotNull(name),Category::getName,name).
                        orderByDesc(Category::getSort)
        );
        // 2,数据返回
        return categoryPage;
    }

    @Override
    public void saveServiceCategory(CategoryDTO categoryDTO) {
        log.info("菜品业务逻辑接口执行: {}","pageServiceCategory");
        // 1,遍历所有菜品判断是否有重复
        List<Category> categories = Db.list(Category.class);

        categories.forEach(item -> {
            //判断名称是否重复
            if (ObjectUtil.equals(item.getName(),
                    categoryDTO.getName())) {

                throw new BusinessException(
                        "抱歉您的名称重复😴😴",
                        ResultStatus.USER_NAME_ERROR
                );

            }
        });
        // 2,无重复就添加
        //dto转换
        Category category = BeanUtil.copyProperties(categoryDTO,Category.class);
        //设置所添加菜品的员工
        category.setEmployeeId(BaseContext.getCurrentId());
        Db.save(category);
    }

    @Override
    public void editServiceCategory(CategoryDTO categoryDTO) {
        log.info("菜品业务逻辑接口执行: {}","editServiceCategory");
        // 1,获取集合进行遍历查看是否名称重复,不包括自身
        List<Category> categories = Db.list(Category.class);
        categories.forEach(item -> {
            //判断是否是自身,自身不修改
            if (ObjectUtil.notEqual(item.getId(),categoryDTO.getId())) {
                //判断名称是否重复
                if (ObjectUtil.equals(item.getName(),
                        categoryDTO.getName())) {

                    throw new BusinessException(
                            "您的名称重复🤗🤗",
                            ResultStatus.USER_NAME_ERROR
                    );

                }
            }
        });
        // 2,进行修改
        //dto转换
        Category category = BeanUtil.copyProperties(categoryDTO,Category.class);
        Db.updateById(category);
    }
}
