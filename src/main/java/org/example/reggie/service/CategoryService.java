package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.domain.dto.CategoryDTO;
import org.example.reggie.domain.po.Category;
import org.example.reggie.domain.po.Employee;

/**
 * 菜品分类表示层接口
 */
public interface CategoryService extends IService<Category> {

    /**
     * 菜品分类分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Category> pageServiceCategory(Integer page,Integer pageSize,String name);

    /**
     * 菜品类型添加
     * @param categoryDTO
     */
    void saveServiceCategory(CategoryDTO categoryDTO);

    /**
     * 菜品类型修改
     * @param categoryDTO
     */
    void editServiceCategory(CategoryDTO categoryDTO);
}
