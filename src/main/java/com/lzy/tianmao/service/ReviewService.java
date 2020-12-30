package com.lzy.tianmao.service;

import com.lzy.tianmao.bean.Review;
import com.lzy.tianmao.dao.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    public List<Review> list(int pid){
        return reviewDao.findAll(pid);
    }

    public int count(int pid){
        return reviewDao.count(pid);
    }

    public void add(String content,int uid,int pid){
        reviewDao.save(content,uid,pid ,new Date());
    }
}
