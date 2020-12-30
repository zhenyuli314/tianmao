package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDao {

    @Select("select * from product where cid=#{cid}")
    List<Product> findAll(Integer cid);

    @Select("select * from product where name like #{keyword}")
    List<Product> findByKeyword(String keyword);

    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("insert into product(`name`,`subTitle`,`originalPrice`,`promotePrice`,`stock`,`cid`,`createDate`) " +
            "values(#{name},#{subTitle},#{originalPrice},#{promotePrice},#{stock},#{cid},#{createDate})")
    void save(Product product);

    @Delete("delete from product where id=#{id}")
    void delete(Integer id);

    @Select("select * from product where id=#{id}")
    Product findById(Integer id);

    @Update("update product set name=#{name},subTitle=#{subTitle},originalPrice=#{originalPrice}," +
            "promotePrice=#{promotePrice},stock=#{stock},cid=#{cid},createDate=#{createDate} where id=#{id}")
    void update(Product product);
}
