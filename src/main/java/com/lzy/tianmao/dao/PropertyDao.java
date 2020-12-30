package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.Category;
import com.lzy.tianmao.bean.Property;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PropertyDao {
    @Select("select * from property where cid=#{cid}")
    List<Property> findAll(Integer cid);

    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("insert into property(`cid`,`name`) values(#{cid},#{name})")
    void save(Property property);

    @Delete("delete from property where id=#{id}")
    void delete(Integer id);

    @Select("select * from property where id=#{id}")
    Property findById(Integer id);

    @Update("update property set name=#{name} where id=#{id}")
    void update(Property property);

}
