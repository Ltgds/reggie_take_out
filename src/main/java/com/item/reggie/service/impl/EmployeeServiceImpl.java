package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.entity.Employee;
import com.item.reggie.mapper.EmployeeMapper;
import com.item.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author Li Guotng
 * @create 2022-07-08 11:13
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
