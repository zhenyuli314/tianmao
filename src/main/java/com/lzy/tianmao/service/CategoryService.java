package com.lzy.tianmao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Category;
import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.bean.ProductImage;
import com.lzy.tianmao.dao.CategoryDao;
import com.lzy.tianmao.dao.ProductDao;
import com.lzy.tianmao.dao.ProductImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDAO;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImageDao productImageDao;

    //给单个product赋值ProductImage
    private void setProductImage(Product product){
        List<ProductImage> productImages = productImageDao.findByPid(product.getId());
        product.setProductImage(productImages.get(0));
    }
    //给多个product赋值ProductImage
    private void setProductImage(List<Product> products){
        for (Product product : products) {
            setProductImage(product);
        }
    }

    //给单个category设置products属性
    public void setProducts(Category category){
        List<Product> products = productDao.findAll(category.getId());
        //给products的 ProductImage属性赋值
        setProductImage(products);
        category.setProducts(products);
    }
    //给多个category设置products属性
    public void setProducts(List<Category> categories){
        for (Category category : categories) {
            setProducts(category);
        }
    }

    //给单个category设置productsByRow属性
    private void setProductsByRow(Category category){
        List<Product> products = productDao.findAll(category.getId());
        int productNumberEachRow = 8;
        List<List<Product>> productsByRow = new ArrayList<>();
        for (int i = 0; i < products.size(); i+=productNumberEachRow) {
            int size = i+productNumberEachRow;
            size= Math.min(size, products.size());
            List<Product> list = products.subList(i, size);
            productsByRow.add(list);
        }
        category.setProductsByRow(productsByRow);
    }
    //给多个category设置productsByRow属性
    public void setProductsByRow(List<Category> categories){
        for (Category category : categories) {
            setProductsByRow(category);
        }
    }


    public PageInfo<Category> list(int start, int size,int navigatePages){
        PageHelper.startPage(start, size,"id desc");
        List<Category> categories = categoryDAO.findAll();
        PageInfo<Category> pageInfo = new PageInfo<>(categories,navigatePages);
        return pageInfo;
    }

    public List<Category> list(){
        return categoryDAO.findAll();
    }

    public void add(Category bean) {
        categoryDAO.save(bean);
    }

    public void delete(Integer id){
        categoryDAO.delete(id);
    }

    public Category findById(Integer id){
        return categoryDAO.findById(id);
    }

    public void update(Category category){
        categoryDAO.update(category);
    }
}
