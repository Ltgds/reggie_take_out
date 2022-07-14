package com.item.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.item.reggie.common.Result;
import com.item.reggie.entity.Category;
import com.item.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li Guotng
 * @create 2022-07-09 21:41
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        log.info("category: {}",category);
        categoryService.save(category);
        return Result.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //添加排序条件,根据sort进行排序
        wrapper.orderByAsc(Category::getSort);

        //进行分页查询
        categoryService.page(pageInfo,wrapper);

        return Result.success(pageInfo);
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long ids) {

        log.info("删除分类,id为: {}",ids);

        categoryService.remove(ids);
        return Result.success("分类信息删除成功");
    }

    /**
     * 根据id来修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        log.info("修改分类信息: {}",category);

        categoryService.updateById(category);
        return Result.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return Result.success(list);
    }

}
