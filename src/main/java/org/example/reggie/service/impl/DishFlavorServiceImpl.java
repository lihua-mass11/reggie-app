package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.domain.po.DishFlavor;
import org.example.reggie.mapper.DishFlavorMapper;
import org.example.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author 唐三
 * description: 菜品口味业务逻辑处理层
 */
@Slf4j
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
                        implements DishFlavorService {
}
