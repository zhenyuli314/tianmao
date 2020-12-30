package com.lzy.tianmao.web;

import com.lzy.tianmao.bean.ProductImage;
import com.lzy.tianmao.service.ProductImageService;
import com.lzy.tianmao.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;

    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> listSingles(@PathVariable("pid") int pid,@RequestParam("type") String type){
        if (type.equals("single")){
            List<ProductImage> singleProductImages = productImageService.listSingleProductImages(pid);
            return singleProductImages;
        }else if (type.equals("detail")){
            List<ProductImage> listDetailProductImages = productImageService.listDetailProductImages(pid);
            return listDetailProductImages;
        }else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/productImages")
    public ProductImage add(@RequestParam("pid") Integer pid,
                            @RequestParam("image") MultipartFile file,
                            @RequestParam("type") String type,
                            HttpServletRequest request) throws IOException {

        //第一步，先将 productImage 对象保存到数据库
        ProductImage productImage = new ProductImage(0, pid, type);
        productImageService.add(productImage);
        File productDir= null;
        File image= null;
        File productSingle_middle=null;
        File productSingle_small=null;

        //第二步：判断当前传来图像类型：single？detail？
        if (type.equals("single")){
            //如果时single，则创建对应的储存路径
            productDir = new File(request.getServletContext().getRealPath("/img/productSingle"));
            //同时single图因为业务需求，同时创建不同分辨率的存储路径
            productSingle_middle = new File(request.getServletContext().getRealPath("/img/productSingle_middle"));
            productSingle_small = new File(request.getServletContext().getRealPath("/img/productSingle_small"));
            }
        else {
            //如果是detail类型，则只需创建一个对应的存储位置
            productDir = new File(request.getServletContext().getRealPath("/img/productDetail"));
            }

        //创建 productSingle 文件夹或者 productDetail文件夹
        if (!productDir.exists()){
            productDir.mkdirs();
        }

        //第三步：将图像以id名命并保存
        image   = new File(productDir, productImage.getId() + ".jpg");
        file.transferTo(image);
        //统一确保真正转成jpg格式的
        BufferedImage bufferedImage = ImageUtil.change2jpg(image);
        ImageIO.write(bufferedImage, "jpg", image);


        //第四步：如果是single类型的，还需要保存不同分辨率的图像到相应的位置
        if (type.equals("single")){
            //创建相应的文件夹
            if (!productSingle_middle.exists()){
                productSingle_middle.mkdirs();
            }
            if (!productSingle_small.exists()){
                productSingle_small.mkdirs();
            }
            //创建file对象
            File imgeSingle_middle = new File(productSingle_middle,productImage.getId() + ".jpg");
            File imgeSingle_small = new File(productSingle_small,productImage.getId() + ".jpg");
            //调整分辨率并保存
            ImageUtil.resizeImage(image, 56,56,imgeSingle_small);
            ImageUtil.resizeImage(image, 217,190,imgeSingle_middle);
        }

        return productImage;
    }

    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id,HttpServletRequest request){

        productImageService.delete(id);

        File productSingle = new File(request.getServletContext().getRealPath("/img/productSingle/"+id+".jpg"));
        File productSingle_middle = new File(request.getServletContext().getRealPath("/img/productSingle_middle/"+id+".jpg"));
        File productSingle_small = new File(request.getServletContext().getRealPath("/img/productSingle_small/"+id+"jpg"));
        File productDetail = new File(request.getServletContext().getRealPath("/img/productDetail/"+id+".jpg"));

        if (productSingle.exists()){
            productSingle.delete();
            productSingle_middle.delete();
            productSingle_small.delete();
        }
        if (productDetail.exists()){
            productDetail.delete();
        }

        return null;
    }
}
