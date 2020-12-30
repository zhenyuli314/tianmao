package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.ProductImage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductImageDao {

    @Select("select * from productimage where `pid`=#{pid} and `type`=#{type}")
    public List<ProductImage> list(int pid,String type);

    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("insert into productimage(`pid`,`type`) values(#{pid},#{type})")
    public void save(ProductImage productImage);

    @Delete("delete from productimage where id = #{id}")
    public void delete(int id);

    @Select("select * from productimage where pid = #{pid} and type='single' ")
    public List<ProductImage> findByPid(int pid);
}
