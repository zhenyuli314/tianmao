package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id=#{id}")
    User findById(int id);

    @Select("select * from user where name=#{name}")
    User findByName(User user);


    @Insert("insert into user(`name`,`password`) values(#{name},#{password})")
    void save(User user);

    @Select("select * from user where name=#{name} and password=#{password}")
    User findByNameAndPassword(User user);
}
