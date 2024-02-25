package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: cyl
 * @description: TODO 地址实现类
 * @date: 2024/2/25 16:00
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.insert(addressBook);
    }

    @Override
    public List<AddressBook> list() {
        return addressBookMapper.list(BaseContext.getCurrentId());
    }

    @Override
    public void defaultAddressBook(Long id) {
        // 先取消之前的默认值
        AddressBook addressBookMapperByDetault = addressBookMapper.getByDetault(BaseContext.getCurrentId());
        addressBookMapperByDetault.setIsDefault(0);
        addressBookMapper.update(addressBookMapperByDetault);

        // 再给当前的数据加上默认值
        AddressBook addressBookDefault = AddressBook.builder().id(id).isDefault(1).build();
        addressBookMapper.update(addressBookDefault);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    @Override
    public void updateById(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.update(addressBook);
    }

    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    @Override
    public AddressBook getDefault() {
        return addressBookMapper.getByDetault(BaseContext.getCurrentId());
    }
}
