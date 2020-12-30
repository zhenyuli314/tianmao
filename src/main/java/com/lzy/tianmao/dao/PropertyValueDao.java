package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.PropertyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PropertyValueDao {

    @Select("select * from propertyvalue where pid = #{pid}")
    List<PropertyValue> findAll(int pid);

    @Update("update propertyvalue set value=#{value} where id=#{id}")
    void update(PropertyValue propertyValue);
}
