package com.item.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.item.reggie.common.Result;
import com.item.reggie.dto.DishDTO;
import com.item.reggie.entity.Category;
import com.item.reggie.entity.Dish;
import com.item.reggie.entity.DishFlavor;
import com.item.reggie.service.CategoryService;
import com.item.reggie.service.DishFlavorService;
import com.item.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Li Guotng
 * @create 2022-07-11 11:11
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info(dishDTO.toString());

        dishService.saveWithFlavor(dishDTO);
        return Result.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {

        //分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDTO> dishDtoPage = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝,并且不拷贝records,因为需要手动处理records
        //recodes要求的泛型(dishDTO)集合与pageInfo(dish)不一样
        BeanUtils.copyProperties(pageInfo, dishDtoPage,"records");

        //单独给id设置对应名称
        List<Dish> records = pageInfo.getRecords();
        List<DishDTO> list = records.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();

            //先把其他属性copy
            BeanUtils.copyProperties(item, dishDTO);

            //获得分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                //设置id对应的name
                dishDTO.setCategoryName(categoryName);
            }

            return dishDTO;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return Result.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<DishDTO> get(@PathVariable Long id) {

        DishDTO dishDTO = dishService.getByIdWithFlavor(id);

        return Result.success(dishDTO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDTO dishDTO) {

        dishService.updateWithFlavor(dishDTO);
        return Result.success("菜品更新成功");
    }

//    /**
//     * 根据条件查询对应的菜品数据
//     * @param dish
//     * @return
//     */
//    @GetMapping("/list")
//    public Result<List<Dish>> list(Dish dish) {
//
//        //构造查询条件
//         LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//
//        //由于有起售和停售的区别,所以只查起售的,即status = 1的
//        queryWrapper.eq(Dish::getStatus, 1);
//
//        //添加排序条件
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(queryWrapper);
//        return Result.success(list);
//    }

    /**
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishDTO>> list(Dish dish) {

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());

        //由于有起售和停售的区别,所以只查起售的,即status = 1的
        queryWrapper.eq(Dish::getStatus, 1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDTO> dishDTOList = list.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();

            BeanUtils.copyProperties(item, dishDTO);

            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDTO.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);

            dishDTO.setFlavors(dishFlavorList);

            return dishDTO;

        }).collect(Collectors.toList());
        return Result.success(dishDTOList);
    }

    /**
     * 根据id删除菜品信息
     *
     * 删除和批量删除可以放在一起
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long ids) {

        dishService.removeWithFlavor(ids);

        return Result.success("菜品删除成功");
    }


}
