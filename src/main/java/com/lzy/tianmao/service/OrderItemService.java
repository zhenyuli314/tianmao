package com.lzy.tianmao.service;

import com.lzy.tianmao.bean.OrderItem;
import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.dao.OrderItemDao;
import com.lzy.tianmao.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;

    public void update(OrderItem orderItem){
        orderItemDao.updateOidById(orderItem.getOid(),orderItem.getId());
    }

    public void add(OrderItem orderItem){
        orderItemDao.save(orderItem);
    }

    //某个用户购物车中商品的数量
    public int countCart(int uid){
        int saleCount = 0;
        List<OrderItem> orderItems = orderItemDao.findByOidAndUid(-2, uid);
        for (OrderItem orderItem : orderItems) {
            saleCount+=orderItem.getNumber();
        }
        return saleCount;
    }

    //某个产品的销售量
    public int countByPid(int pid){
        int saleCount = 0;
        List<OrderItem> orderItems = orderItemDao.findByPid(pid);
        for (OrderItem orderItem : orderItems) {
            saleCount+=orderItem.getNumber();
        }
        return saleCount;
    }


    public OrderItem findById(int id){
        return orderItemDao.findById(id);
    }

    public List<OrderItem> list(int oid){
        List<OrderItem> orderItems = orderItemDao.findByOid(oid);
        setProducts(orderItems);
        return orderItems;
    }
    public List<OrderItem> list(int oid, int uid){
        List<OrderItem> orderItems = orderItemDao.findByOidAndUid(oid,uid);
        setProducts(orderItems);
        return orderItems;
    }

    public OrderItem list(int uid,int pid,int oid){
        return orderItemDao.findByUidPid(uid,pid,oid);
    }

    //给单个orderItem设置product属性
    private void setProduct(OrderItem orderItem){
        Product product = productDao.findById(orderItem.getPid());

        orderItem.setProduct(product);
    }
    //给多个orderItem设置product属性
    public void setProducts(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems) {
            setProduct(orderItem);
        }
    }

    //修改某用户购物车中商品的数量
    public void updateCart(int number,int uid,int pid){
        orderItemDao.updateByPidUid(number,uid,pid, -2);
    }

    //根据id删除订单项
    public void deleteById(int oiid){
        orderItemDao.deleteById(oiid);
    }
}
