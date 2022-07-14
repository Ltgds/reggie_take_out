package com.item.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.item.reggie.entity.Category;

/**
 * @author Li Guotng
 * @create 2022-07-09 21:38
 */
public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
