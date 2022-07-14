package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.common.CustomException;
import com.item.reggie.entity.Category;
import com.item.reggie.entity.Dish;
import com.item.reggie.entity.Setmeal;
import com.item.reggie.mapper.CategoryMapper;
import com.item.reggie.service.CategoryService;
import com.item.reggie.service.DishService;
import com.item.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Li Guotng
 * @create 2022-07-09 21:39
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类,删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件,根据分类id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        //查询当前分类是否关联了菜品,如果已经关联,抛出一个业务异常
        if (count1 > 0) {
            //已经关联菜品,抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品,不能删除");
        }


        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件,根据分类id查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        //查询当前分类是否关联了套餐,如果已经关联,抛出一个业务异常
        if (count2 > 0) {
            //已经关联套餐,抛出一个业务异常
            throw new CustomException(("当前分类下关联了套餐,不能删除"));
        }

        //正常删除
        super.removeById(id);
    }
}
