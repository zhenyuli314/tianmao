package com.lzy.tianmao.web;

import com.github.pagehelper.PageInfo;
import com.lzy.tianmao.bean.Order;
import com.lzy.tianmao.bean.OrderItem;
import com.lzy.tianmao.bean.Product;
import com.lzy.tianmao.service.OrderItemService;
import com.lzy.tianmao.service.OrderService;
import com.lzy.tianmao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @GetMapping("/orders")
    public PageInfo<Order> listOrders(@RequestParam(value = "start",defaultValue = "1") int start,
                                      @RequestParam(value = "size",defaultValue = "5") int size,
                                      @RequestParam(value = "navigatePages",defaultValue = "5") int navigatePages){
        PageInfo<Order> orderPageInfo = orderService.list(start, size, navigatePages);

        List<Order> orders = orderPageInfo.getList();
        orderService.setOrderItems(orders);
        //给每个orderItem设置product属性
        for (Order order : orders) {
            List<OrderItem> orderItems = order.getOrderItems();
            orderItemService.setProducts(orderItems);
            //给每个产品设置图像
            for (OrderItem orderItem : orderItems) {
                Product product = orderItem.getProduct();
                productService.setFirstProdutImage(product);
            }
        }
        return orderPageInfo;
    }

    @PutMapping("/deliveryOrder/{oid}")
    public Order deliveryOrder(@PathVariable("oid") int oid){
        Order order = orderService.deliveryOrder(oid);
        return order;
    }
}
