package com.item.reggie.dto;

import com.item.reggie.entity.Dish;
import com.item.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Guotng
 * @create 2022-07-11 15:48
 */
@Data
public class DishDTO extends Dish {

    //菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
