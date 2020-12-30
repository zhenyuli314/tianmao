package com.lzy.tianmao.interceptor;

import com.lzy.tianmao.bean.Category;
import com.lzy.tianmao.bean.User;
import com.lzy.tianmao.service.CategoryService;
import com.lzy.tianmao.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class OtherInterCepter implements HandlerInterceptor {

    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");


        if (null!=user){
            int countCart = orderItemService.countCart(user.getId());
            session.setAttribute("countCart",countCart);
        }else {
            session.removeAttribute("countCart");
        }

        List<Category> categories = categoryService.list();
        request.getServletContext().setAttribute("categories",categories);
    }

}
