package com.lzy.tianmao.web;

import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/categories/{cid}/products")
    public PageInfo<Product> list(@PathVariable("cid") int cid,
                                  @RequestParam(name = "start",defaultValue = "0") int start,
                                  @RequestParam(name = "size",defaultValue = "5") int size,
                                  @RequestParam(name = "navigatePages",defaultValue = "5") int navigatePages){
        PageInfo<Product> pageInfo = productService.list(cid, start, size, navigatePages);
        return pageInfo;
    }

    @PostMapping("/products")
    public Product add(@RequestBody Product product){
        product.setCreateDate(new Date());
        productService.add(product);
        return product;
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id){
        productService.delete(id);
        return null;
    }

    @GetMapping("/products/{id}")
    public Product findById(@PathVariable("id") int id){
        Product product = productService.findById(id);
        return product;
    }

    @PutMapping("/products")
    public Product update(@RequestBody Product product){
        productService.update(product);
        return product;
    }
}
