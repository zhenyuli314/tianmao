package com.lzy.tianmao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Order;
import com.lzy.tianmao.bean.OrderItem;
import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.bean.User;
import com.lzy.tianmao.dao.OrderDao;
import com.lzy.tianmao.dao.OrderItemDao;
import com.lzy.tianmao.dao.ProductDao;
import com.lzy.tianmao.dao.UserDao;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    public static final String waitPay="waitPay";
    public static final String waitDelivery="waitDelivery";
    public static final String waitConfirm="waitConfirm";
    public static final String waitReview="waitReview";
    public static final String finish="finish";
    public static final String delete="delete";

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;

    public Order findById(int id){
        Order order = orderDao.findById(id);
        setTotal(order);

        return order;
    }

    //开启事务支持
    @Transactional(rollbackFor = Exception.class)
    public float  save(Order order,User user){
        orderDao.add(order);

        float total=0;
        List<OrderItem> orderItems = orderItemService.list(-2, user.getId());
        for (OrderItem orderItem : orderItems) {
            //创建订单，将订单id设置为订单项的 oid
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);

            int pid = orderItem.getPid();
            Product product = productService.findById(pid);
            total+=orderItem.getNumber()*product.getPromotePrice();
        }
        return total;
    }

    public void setOrderItem(Order order){
        List<OrderItem> orderItems = orderItemDao.findByOid(order.getId());
        order.setOrderItems(orderItems);
    }
    //给order设置orderItems
    public void setOrderItems(List<Order> orders){
        for (Order order : orders) {
            setOrderItem(order);
        }
    }


    //支付
    public Order payOrder(int oid){
        Order order = orderDao.findById(oid);
        order.setPayDate(new Date());
        order.setStatus(waitDelivery);
        orderDao.updateStatus(order);
        return order;
    }

    //发货
    public Order deliveryOrder(int oid){
        Order order = orderDao.findById(oid);
        order.setDeliveryDate(new Date());
        order.setStatus(waitConfirm);
        orderDao.updateStatus(order);
        return order;
    }

    //确认收货
    public Order confirmPay(int oid){
        Order order = orderDao.findById(oid);
        order.setConfirmDate(new Date());
        order.setStatus(waitReview);
        orderDao.updateStatus(order);
        return order;
    }
    //删除订单（修改status为delete）
    public Order deleteOrder(int oid){
        Order order = orderDao.findById(oid);
        order.setStatus(delete);
        orderDao.updateStatus(order);
        return order;
    }

    public PageInfo<Order> list(int start, int size,int navigatePages){
        PageHelper.startPage(start, size);
        List<Order> orders = orderDao.findAll();
        //设置user
        setUsers(orders);
        //设置total和totalNumber属性
        setTotals(orders);

        PageInfo<Order> orderPageInfo = new PageInfo<>(orders,navigatePages);
        return orderPageInfo;
    }

    //只查找非delete的指定用户的所有订单
    public List<Order> listByUid(int uid){
        List<Order> orders = orderDao.findByUid(uid,"delete");
        setTotals(orders);
        return orders;
    }

    //给单个order设置user属性
    private void setUser(Order order){
        User user = userDao.findById(order.getUid());
        order.setUser(user);
    }
    //给多个order设置user
    private void setUsers(List<Order> orders){
        for (Order order : orders) {
            setUser(order);
        }
    }

    //给单个order设置total和totalNumber属性
    private void setTotal(Order order){
        List<OrderItem> orderItems = orderItemDao.findByOid(order.getId());

        //总数量
        int totalNumber=0;
        //总价格
        double total = 0;
        for (OrderItem orderItem : orderItems) {

            //1.设置商品数量
            totalNumber += orderItem.getNumber();

            //2.设置总价格
            //2.1 根据pid查询商品价格
            Product product = productDao.findById(orderItem.getPid());
            //2.2 单价
            double price = product.getPromotePrice();
            //2.3 总价
            total+=price * orderItem.getNumber();

        }

        //保留两位小数
        DecimalFormat df   = new DecimalFormat("######0.00");

        order.setTotalNumber(totalNumber);
        order.setTotal(Double.parseDouble(df.format(total)));
    }

    //给多个个order设置total和totalNumber属性
    private void setTotals(List<Order> orders){
        for (Order order : orders) {
            setTotal(order);
        }
    }
}
