package com.item.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.reggie.entity.AddressBook;
import com.item.reggie.mapper.AddressBookMapper;
import com.item.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author Li Guotng
 * @create 2022-07-13 10:41
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
