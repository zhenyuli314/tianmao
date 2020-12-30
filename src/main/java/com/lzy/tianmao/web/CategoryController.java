package com.lzy.tianmao.web;

import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Category;
import com.lzy.tianmao.service.CategoryService;
import com.lzy.tianmao.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 获取数据控制器（为了前后端分离）
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public PageInfo<Category> list(@RequestParam(name = "start",defaultValue = "0") int start,
                                   @RequestParam(name = "size",defaultValue = "5") int size,
                                   @RequestParam(name = "navigatePages",defaultValue = "5") int navigatePages){
//        List<Category> categories = categoryService.list();
        PageInfo<Category> pageInfo = categoryService.list(start, size, navigatePages);
        return pageInfo;
    }

    @PostMapping("/categories")
    public Category add(Category category, @RequestParam(value = "image") MultipartFile file, HttpServletRequest request) throws IOException {

        categoryService.add(category);
        saveOrUpdateImageFile(category,file,request);
        return category;
    }

    public void saveOrUpdateImageFile(Category category, MultipartFile file, HttpServletRequest request) throws IOException {
        File dir = new File(request.getServletContext().getRealPath("img/category"));

        File image = new File(dir,category.getId()+".jpg");

//        if (!image.exists()){
//            image.mkdirs();
//        }
        if(!image.getParentFile().exists())
            image.getParentFile().mkdirs();

        file.transferTo(image);
        BufferedImage bufferedImage = ImageUtil.change2jpg(image);
        ImageIO.write(bufferedImage, "jpg", image);
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") Integer id,HttpServletRequest request){
        System.out.println(id);

        categoryService.delete(id);
        File file = new File(request.getServletContext().getRealPath("img/category"));
        File image = new File(file, id + ".jpg");
        image.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category findById(@PathVariable("id") Integer id){
        Category category = categoryService.findById(id);
        return category;
    }

    @PutMapping("/categories")
    public Category update(Category category,@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {

        System.out.println(category);
        System.out.println(file);

        categoryService.update(category);
        if (file!=null){
            saveOrUpdateImageFile(category,file,request);
        }
        return category;
    }
}
