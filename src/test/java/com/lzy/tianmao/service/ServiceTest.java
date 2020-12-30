package com.lzy.tianmao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {
        PageHelper.startPage(0 , 2,"id desc");
        List<Category> categories = categoryService.list();

        PageInfo<Category> pageInfo = new PageInfo<>(categories,5);
//        System.out.println(categories.forEach(System.out::println));
        System.out.println(pageInfo.getList());
    }

}
