package com.item.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.item.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Li Guotng
 * @create 2022-07-08 11:11
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
