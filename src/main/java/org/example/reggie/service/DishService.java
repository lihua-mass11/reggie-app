package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.domain.dto.DishDTO;
import org.example.reggie.domain.po.Dish;
import org.example.reggie.domain.vo.DishVO;

/**
 * 菜品表示层接口
 */
public interface DishService extends IService<Dish> {

    /**
     * 菜品展示分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<DishVO> dishServicePage(Integer page, Integer pageSize, String name);

    /**
     * 菜品添加
     * @param dishDTO
     * @return
     */
    void saveServiceDish(DishDTO dishDTO);
}
