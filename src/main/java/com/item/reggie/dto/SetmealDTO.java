package com.item.reggie.dto;

import com.item.reggie.entity.Setmeal;
import com.item.reggie.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Li Guotng
 * @create 2022-07-12 10:05
 */
@Data
public class SetmealDTO extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
