package com.lzy.tianmao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Review;
import com.lzy.tianmao.bean.User;
import com.lzy.tianmao.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findById(int id){
        return userDao.findById(id);
    }

    //根据传入的 reviews 集合查询对应的用户集合（**有顺序**）
    public List<User> listByReviews(List<Review> reviews){
        ArrayList<User> users = new ArrayList<>();
        for (Review review : reviews) {
            User user = userDao.findById(review.getUid());
            users.add(user);
        }
        return users;
    }

    public PageInfo<User> list(int start, int size, int navigatePages){
        PageHelper.startPage(start, size);
        List<User> users = userDao.findAll();
        PageInfo<User> userPageInfo = new PageInfo<>(users,navigatePages);
        return userPageInfo;
    }

    //判断用户是否已存在,存在：1，不存在：0
    public int userExist(User user){
        User user1 = userDao.findByName(user);
        if (user1==null){
            return 0;
        }else {
            return 1;
        }
    }

    public void add(User user){
        userDao.save(user);
    }

    public User get(User user){
        return userDao.findByNameAndPassword(user);
    }

    public User get(String userName){
        User user = new User();
        user.setName(userName);
        return userDao.findByName(user);
    }
}
