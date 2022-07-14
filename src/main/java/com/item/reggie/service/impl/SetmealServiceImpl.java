package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.common.CustomException;
import com.item.reggie.dto.SetmealDTO;
import com.item.reggie.entity.Setmeal;
import com.item.reggie.entity.SetmealDish;
import com.item.reggie.mapper.SetmealMapper;
import com.item.reggie.service.SetmealDishService;
import com.item.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Li Guotng
 * @create 2022-07-10 15:19
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐,同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        //保存套餐的基本信息,操作setmeal,执行insert操作
        this.save(setmealDTO);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //setmealDishs里面存的只有dishId和name等,没有setmealId
        //所以需要将每个setmealDish单独拿出来,给里面的setmealId赋值
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDTO.getId());
            return item;
        }).collect(Collectors.toList());


        //保存套餐和菜品的关联关系,操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐,同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态,确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);

        //如果不能删除,抛出一个业务异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中,不能删除");
        }

        //如果可以删除,先删除套餐表中的数据--setmeal
        this.removeByIds(ids);

        //删除关系表中的数据--setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }
}
