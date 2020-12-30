package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ReviewDao {

    @Select("select * from review where pid=#{pid}")
    List<Review> findAll(int pid);

    @Select("select count(*) from review where pid=#{pid}")
    int count(int pid);

    @Insert("insert into review(`content`,`uid`,`pid`,`createDate`) values(#{content},#{uid},#{pid},#{createDate})")
    void save(String content, int uid, int pid, Date createDate);
}
