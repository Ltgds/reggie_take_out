package com.item.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.item.reggie.dto.DishDTO;
import com.item.reggie.entity.Dish;

import java.util.List;

/**
 * @author Li Guotng
 * @create 2022-07-10 15:17
 */
public interface DishService extends IService<Dish> {

    //新增菜品,同时插入菜品对应的口味数据,需要操作两张表:dish、dishFlavor
    public void saveWithFlavor(DishDTO dishDTO);

    //根据id查询菜品信息和对应口味信息
    public DishDTO getByIdWithFlavor(Long id);

    //更新菜品信息
    public void updateWithFlavor(DishDTO dishDTO);

    //根据id删除菜品信息
    public void removeWithFlavor(Long ids);

    //根据数组ids删除菜品信息
//    public void removeWithFlavorBatch(List<Long> ids);
}
