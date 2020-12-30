package com.lzy.tianmao.service;

import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.bean.ProductImage;
import com.lzy.tianmao.dao.ProductImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    @Autowired
    ProductImageDao productImageDao;
    public static final String type_single = "single";
    public static final String type_detail = "detail";

    public List<ProductImage> listSingleProductImages(int pid){
        List<ProductImage> productImages = productImageDao.list(pid,type_single);
        return productImages;
    }

    public List<ProductImage> listDetailProductImages(int pid){
        List<ProductImage> productImages = productImageDao.list(pid,type_detail);
        return productImages;
    }

    public void add(ProductImage productImage){
        productImageDao.save(productImage);
    }

    public void delete(int id){
        productImageDao.delete(id);
    }
}
