package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryDao {
    @Select("select * from category")
    List<Category> findAll();

    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("insert into category(`name`) values(#{name})")
    void save(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(Integer id);

    @Select("select * from category where id=#{id}")
    Category findById(Integer id);

    @Update("update category set name=#{name} where id=#{id}")
    void update(Category category);

}
