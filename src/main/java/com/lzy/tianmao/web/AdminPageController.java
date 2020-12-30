package com.lzy.tianmao.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器
 */
@Controller
public class AdminPageController {

    //访问主页则重定向到listCategory.html页面
    @GetMapping("/admin")
    public String admin(){
        return "redirect:/listCategory";
    }

    @GetMapping("/listCategory")
    public String listCategory(){
        return "admin/listCategory";
    }

    @GetMapping("/editCategory")
    public String editCategory(){
        return "admin/editCategory";
    }


    @GetMapping("/listProperty")
    public String listProperty(){
        return "admin/listProperty";
    }

    @GetMapping("/editProperty")
    public String editProperty(){
        return "admin/editProperty";
    }

    @GetMapping("/listProduct")
    public String listProduct(){
        return "admin/listProduct";
    }

    @GetMapping("/editProduct")
    public String editProduct(){
        return "admin/editProduct";
    }

    @GetMapping("/listProductImage")
    public String listProductImage(){
        return "admin/listProductImage";
    }

    @GetMapping("/editPropertyValue")
    public String editPropertyValue(){
        return "admin/editPropertyValue";
    }

    @GetMapping("/listUser")
    public String listUser(){
        return "admin/listUser";
    }

    @GetMapping("/listOrder")
    public String listOrder(){
        return "admin/listOrder";
    }
}
