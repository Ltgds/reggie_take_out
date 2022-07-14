package com.item.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.item.reggie.entity.Orders;

/**
 * @author Li Guotng
 * @create 2022-07-14 10:52
 */
public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
