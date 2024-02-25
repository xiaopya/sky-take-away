package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: cyl
 * @description: TODO 地址簿
 * @date: 2024/2/25 15:57
 */
@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api("地址簿")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("添加地址")
    public Result add(@RequestBody AddressBook addressBook) {
        log.info("添加地址：{}", addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("更新地址信息")
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("更新地址信息：{}", addressBook);
        addressBookService.updateById(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("地址列表")
    public Result<List<AddressBook>> list() {
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<AddressBook> detail(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result defaultAddressBook(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址：{}", addressBook.getId());
        addressBookService.defaultAddressBook(addressBook.getId());
        return Result.success();
    }

    @GetMapping("/default")
    @ApiOperation("获取默认地址")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = addressBookService.getDefault();
        return Result.success(addressBook);
    }

    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        addressBookService.deleteById(id);
        return Result.success();
    }
}
