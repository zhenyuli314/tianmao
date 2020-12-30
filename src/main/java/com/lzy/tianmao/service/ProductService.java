package com.lzy.tianmao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.bean.ProductImage;
import com.lzy.tianmao.dao.ProductDao;
import com.lzy.tianmao.dao.ProductImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    ProductImageDao productImageDao;

    //给单个产品设置图像
    public void setFirstProdutImage(Product product){
        Integer pid = product.getId();
        List<ProductImage> productImages = productImageDao.findByPid(pid);
        product.setProductImage(productImages.get(0));
    }
    //给产品集合设置产品图像
    public void setFirstProdutImages(List<Product> products){
        for (Product product : products) {
            setFirstProdutImage(product);
        }
    }

    public PageInfo<Product> list(int cid, int start, int size, int navigatePages){
        PageHelper.startPage(start,size);
        List<Product> products = productDao.findAll(cid);
        //调用setFirstProdutImages 为产品设置图像
        setFirstProdutImages(products);
        PageInfo<Product> pageInfo = new PageInfo<>(products,navigatePages);
        return pageInfo;
    }
    public List<Product> list(int cid ){
        List<Product> products = productDao.findAll(cid);
        return products;
    }

    public List<Product> list(String keyword){
        keyword = "%"+keyword+"%";
        return productDao.findByKeyword(keyword);
    }

    public Product findById(int id){
        Product product = productDao.findById(id);
        setFirstProdutImage(product);
        return product;
    }

    public void add(Product product){
        productDao.save(product);
    }

    public void delete(int id){
        productDao.delete(id);
    }

    public void update(Product product){
        productDao.update(product);
    }


}
