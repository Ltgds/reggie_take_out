package com.item.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.item.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Li Guotng
 * @create 2022-07-14 10:51
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
