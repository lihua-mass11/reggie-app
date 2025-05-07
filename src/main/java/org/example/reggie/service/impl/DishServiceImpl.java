package org.example.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.example.reggie.common.ResultStatus;
import org.example.reggie.domain.dto.DishDTO;
import org.example.reggie.domain.po.Category;
import org.example.reggie.domain.po.Dish;
import org.example.reggie.domain.po.DishFlavor;
import org.example.reggie.domain.vo.DishVO;
import org.example.reggie.exceptions.BusinessException;
import org.example.reggie.exceptions.SystemException;
import org.example.reggie.mapper.DishMapper;
import org.example.reggie.service.CategoryService;
import org.example.reggie.service.DishFlavorService;
import org.example.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author 唐三
 * description: 菜品查看业务逻辑处理层
 */
@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
                        implements DishService {

    //菜品口味
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Page<DishVO> dishServicePage(Integer page, Integer pageSize, String name) {
        log.info("菜品查看业务逻辑接口执行: {}","dishServicePage");
        //分页
        Page<Dish> dishPage = new Page<>(page,pageSize);
        try {
            //获取菜品
            this.page(
                    dishPage,
                    new LambdaQueryWrapper<Dish>().
                            like(ObjectUtil.isNotNull(name),Dish::getName,name)
            );
        } catch (Exception ex) {
            throw new SystemException(
                    "查找失败,肯是服务器出现问题了😖😖",
                    ResultStatus.ENGINE_ERROR
            );
        }
        Page<DishVO> dishVOPage = new Page<>();

        //转换vo,忽略这个records我们自己添加
        BeanUtil.copyProperties(dishPage,dishVOPage,"records");
        List<Dish> dishes = dishPage.getRecords();
        List<DishVO> dishVOS = dishes.stream().map(item -> {
            //转为DishVO
            DishVO dishVO = new DishVO();
            //转vo
            BeanUtil.copyProperties(item,dishVO);
            //获取id
            Long categoryId = dishVO.getCategoryId();
            //根据id查询菜品分类
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                dishVO.setCategoryName(category.getName());
            }

            return dishVO;
        }).collect(Collectors.toList());
        dishVOPage.setRecords(dishVOS);

        //判断数据是否为空
        if (dishPage.getRecords().size() == 0) {
            System.out.println("为空");
            throw new BusinessException(
                    "抱歉您的数据为空😋😋",
                    ResultStatus.Empty_VAR_ERROR
            );
        }

        return dishVOPage;
    }

    @Override
    public void saveServiceDish(DishDTO dishDTO) {
        //dto转Dish
        Dish dish = BeanUtil.copyProperties(dishDTO,Dish.class);
        //生成菜品商标
        dish.setCode(String.valueOf(UUID.randomUUID()).substring(0,15));
        //顺序
        dish.setSort(1);
        //dto转DishFlavor
        List<DishFlavor> dishFlavors = BeanUtil.copyToList(dishDTO.getFlavors(),DishFlavor.class);
        System.out.println("dish: " + dish);
        System.out.println("口味:" + dishFlavors);
        //获取所有菜品,判断名称是否重复
        List<Dish> dishes = this.list();
        if (!dishes.isEmpty()) {
            dishes.forEach(item -> {
                if (Objects.equals(dish.getName(),item.getName())) {
                    throw new BusinessException(
                            "您的菜品名称重复😡😡",
                            ResultStatus.NAME_REPEATED_ERROR
                    );
                }
            });
        }
        //添加菜品
        Db.save(dish);
        //获取当前菜品id,回自动填充id
        Long dishId = dish.getId();
        //将菜品Id封装到DishFlavor,数据查出
        List<DishFlavor> flavors = dishFlavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
