package com.item.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.item.reggie.dto.SetmealDTO;
import com.item.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author Li Guotng
 * @create 2022-07-10 15:17
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐,同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    public void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 删除套餐,同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
