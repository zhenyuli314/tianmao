package com.lzy.tianmao.web;

import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.User;
import com.lzy.tianmao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public PageInfo<User> list(@RequestParam(name = "start",defaultValue = "1") int start,
                               @RequestParam(name = "size",defaultValue = "5") int size,
                               @RequestParam(name = "navigatePages",defaultValue = "5") int navigatePages){
        PageInfo<User> pageInfo = userService.list(start, size, navigatePages);
        return pageInfo;
    }

}
