package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderDao {

    @Select("select * from order_")
    List<Order> findAll();

    @Select("select * from order_ where id = #{id}")
    Order findById(int id);

    @Select("select * from order_ where uid = #{uid} and status != #{status} ")
    List<Order> findByUid(int uid,String status);

    @Update("update order_ set status=#{status},payDate=#{payDate},deliveryDate=#{deliveryDate}," +
            "confirmDate=#{confirmDate} where id=#{id}")
    void updateStatus(Order order);

    @SelectKey(statement = {"select last_insert_id()"}, keyProperty = "id", before = false, resultType = int.class)
    @Insert("insert into order_ values(#{id},#{orderCode},#{address},#{post},#{receiver},#{mobile},#{userMessage}," +
            "#{createDate},#{payDate},#{deliveryDate},#{confirmDate},#{uid},#{status})")
    void add(Order order);
}
