package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.entity.User;
import com.item.reggie.mapper.UserMapper;
import com.item.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Li Guotng
 * @create 2022-07-12 20:52
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
