package com.lzy.tianmao.interceptor;

import com.lzy.tianmao.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (null!=user && user.getName().equals("admin")){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }
}
