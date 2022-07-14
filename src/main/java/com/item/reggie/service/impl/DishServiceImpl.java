package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.dto.DishDTO;
import com.item.reggie.entity.Dish;
import com.item.reggie.entity.DishFlavor;
import com.item.reggie.mapper.DishMapper;
import com.item.reggie.service.DishFlavorService;
import com.item.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Li Guotng
 * @create 2022-07-10 15:18
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品同时来保存对应的口味数据
     * @param dishDTO
     */
    @Transactional  //进行事务的控制
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        //保存菜品的基本信息到菜品表dish
        this.save(dishDTO);

        Long dishId = dishDTO.getId(); //菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //给flavors中每个值加入id,并重新转为List
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜皮口味表 dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDTO getByIdWithFlavor(Long id) {
        //查询菜品基本信息,从dish表查询
        Dish dish = this.getById(id);

        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish, dishDTO); //将dish拷贝到dishDto

        //再单独为dishDto赋flavor值
        //查询当前菜品对应的口味信息,从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());  //比较dishId和dish表中的id
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDTO.setFlavors(flavors);

        return dishDTO;
    }


    /**
     * 更新菜品信息和对应的口味信息
     * @param dishDTO
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        //更新dish表基本信息
        this.updateById(dishDTO);

        //清理当前菜品对应口味数据--dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据--dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDTO.getFlavors();//这样最终保存的是name和value,dishId没有值
        //遍历flavors,把每一项重新set一个dishId
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDTO.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors); //批量保存
    }

    /**
     * 根据id删除菜品信息
     * @param ids
     */
    @Override
    public void removeWithFlavor(Long ids) {
        //删除dish表中的数据
        this.removeById(ids);

        //删除dish_flavor表中的数据
        //根据ids查找dish_flavor表中对应的id
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, ids);
        dishFlavorService.remove(queryWrapper);
    }

    /**
     * 根据数组ids信息删除菜品信息
     * @param ids
     */
//    @Override
//    public void removeWithFlavorBatch(List<Long> ids) {
//
//    }
}
