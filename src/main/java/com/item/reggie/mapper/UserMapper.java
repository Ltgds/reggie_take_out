package com.item.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.item.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Li Guotng
 * @create 2022-07-12 20:51
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
