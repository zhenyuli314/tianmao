package com.lzy.tianmao.dao;

import com.lzy.tianmao.bean.OrderItem;
import org.apache.ibatis.annotations.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderItemDao {

    @Select("select * from orderitem where id=#{id}")
    OrderItem findById(int id);

    @Select("select * from orderitem where oid=#{oid}")
    List<OrderItem> findByOid(int oid);

    @Select("select * from orderitem where pid = #{pid}")
    List<OrderItem> findByPid(int pid);

    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("insert into orderitem(`pid`,`oid`,`uid`,`number`) " +
            "values(#{pid},#{oid},#{uid},#{number})")
    void save(OrderItem orderItem);

    @Select("select * from orderitem where oid=#{oid} and uid=#{uid}")
    List<OrderItem> findByOidAndUid(int oid, int uid);

    @Update("UPDATE orderitem SET number=#{number}  WHERE uid=#{uid} AND pid=#{pid} and oid=#{oid} ")
    void updateByPidUid(int number,int uid,int pid,int oid);

    @Update("UPDATE orderitem SET oid=#{oid}  WHERE id=#{id}")
    void updateOidById(int oid,int id);

    @Delete("delete from orderitem where id=#{id}")
    void deleteById(int id);

    @Select("select * from orderitem where uid=#{uid} and pid=#{pid} and oid=#{oid}")
    OrderItem findByUidPid(int uid,int pid,int oid);

    @Select("select * from orderitem where uid=#{uid} and oid=#{oid}")
    List<OrderItem> findByUid(int uid,int oid);

}
