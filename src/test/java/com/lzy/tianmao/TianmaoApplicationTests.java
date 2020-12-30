package com.lzy.tianmao;

import com.lzy.tianmao.bean.Category;
import com.lzy.tianmao.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TianmaoApplicationTests {

    @Autowired
    CategoryService categoryService;

    @Test
    void contextLoads() {
        List<Category> categories = categoryService.list();
        System.out.println(categories);

    }

}
