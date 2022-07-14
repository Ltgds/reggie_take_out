package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.entity.ShoppingCart;
import com.item.reggie.mapper.ShoppingCartMapper;
import com.item.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author Li Guotng
 * @create 2022-07-13 21:22
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
