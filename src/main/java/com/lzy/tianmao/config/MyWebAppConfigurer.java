package com.lzy.tianmao.config;

import com.lzy.tianmao.interceptor.AdminInterceptor;
import com.lzy.tianmao.interceptor.LoginInterceptor;
import com.lzy.tianmao.interceptor.OtherInterCepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    OtherInterCepter otherInterCepter;

    @Autowired
    MyArguementResolver myArguementResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/buy")
                .addPathPatterns("/cart")
                .addPathPatterns("/bought")

                .addPathPatterns("/forebuyone")
                .addPathPatterns("/forebuy")
                .addPathPatterns("/foreaddCart")
                .addPathPatterns("/forechangeOrderItem")
                .addPathPatterns("/forecart");

        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin")
                .addPathPatterns("/listCategory")
                .addPathPatterns("/editCategory")
                .addPathPatterns("/listProperty")
                .addPathPatterns("/editProperty")
                .addPathPatterns("/listProduct")
                .addPathPatterns("/editProduct")
                .addPathPatterns("/listProductImage")
                .addPathPatterns("/editPropertyValue")
                .addPathPatterns("/listUser")
                .addPathPatterns("/listOrder");

        registry.addInterceptor(otherInterCepter)
                .addPathPatterns("/**");
    }

    //添加参数解析器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(myArguementResolver);
    }
}
