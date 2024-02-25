package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    /**
     * 添加地址
     */
    void add(AddressBook addressBook);

    List<AddressBook> list();

    void defaultAddressBook(Long id);

    AddressBook getById(Long id);

    void updateById(AddressBook addressBook);

    void deleteById(Long id);

    AddressBook getDefault();
}
