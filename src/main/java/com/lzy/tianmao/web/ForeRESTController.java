package com.lzy.tianmao.web;

import com.lzy.tianmao.bean.*;
import com.lzy.tianmao.config.annotation.CheckLogin;
import com.lzy.tianmao.service.*;
import com.lzy.tianmao.util.Result;
import com.lzy.tianmao.util.comparator.*;
import javafx.beans.binding.ObjectExpression;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ForeRESTController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;


    @GetMapping("/forehome")
    public List<Category> home() {
        List<Category> categories = categoryService.list();
        categoryService.setProducts(categories);
        categoryService.setProductsByRow(categories);
        return categories;
    }

    @PostMapping("/foreregister")
    public Result register(@RequestBody User user) {
        //1、通过参数User获取浏览器提交的账号密码
        //2、通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
        String name = HtmlUtils.htmlEscape(user.getName());
        user.setName(name);
        //3、根据用户名查询是否可用
        int code = userService.userExist(user);
        //3.1 如果已经存在，就返回Result.fail,并带上 错误信息
        //3.2 如果不存在，则加入到数据库中，并返回 Result.success()
        if (code == 0) {
            userService.add(user);
            return Result.success();
        } else {
            String message = "用户名已被占用";
            return Result.fail(message);
        }
    }

    @PostMapping("/forelogin")
    public Result login(@RequestBody User user, HttpSession session) {
        //1.通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
        String name = HtmlUtils.htmlEscape(user.getName());
//        user.setName(name);
//        User user1 = userService.get(user);
        String password = user.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try {
            subject.login(token);
            User user1 = userService.get(user);
            session.setAttribute("user", user1);
            return Result.success(user);
        }catch (AuthenticationException e){
            return Result.fail("用户名或密码不正确");
        }
    }

    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid) {

        Product product = productService.findById(pid);

        List<ProductImage> singleProductImages = productImageService.listSingleProductImages(pid);
        List<ProductImage> detailProductImages = productImageService.listDetailProductImages(pid);
        product.setSingleProductImages(singleProductImages);
        product.setDetailProductImages(detailProductImages);

        //给product设置产品属性值
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        product.setPropertyValues(propertyValues);

        //给product设置评价属性值
        List<Review> reviews = reviewService.list(pid);
        product.setReviews(reviews);

        //获取该商品评价对应的用户
        List<User> users = userService.listByReviews(reviews);

        //给product设置评价数量
        product.setReviewCount(reviewService.count(pid));

        //给product设置销售数量
        product.setSaleCount(orderItemService.countByPid(pid));

        //类别 -> 产品 是一对多，所以把类别单独拿出来
        Category category = categoryService.findById(product.getCid());

        //根据 产品属性值集合 获取对应的 产品属性名集合
        List<Property> properties = propertyService.findByPropertyValues(propertyValues);

        HashMap<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("category", category);
        //properties跟产品表没有直接对应关系，所以单独放出来
        map.put("properties", properties);
        map.put("users", users);

        return Result.success(map);
    }

    @GetMapping("/forecheckLogin")
    public Object foreCheckLogin(HttpSession session) {
        Object user = session.getAttribute("user");
        if (null == user) {
            return Result.fail("请先登录");
        } else {
            return Result.success();
        }
    }

    @GetMapping("/forecategory/{cid}")
    public Object foreCategory(@PathVariable("cid") int cid, @RequestParam("sort") String sort) {
        System.out.println("cid==" + cid);
        System.out.println("sort++" + sort);

        Category category = categoryService.findById(cid);
        if (category != null) {
            //1.给category设置products属性
            categoryService.setProducts(category);
            List<Product> products = category.getProducts();
            //2.category的products的FirstProdutImages设置
            productService.setFirstProdutImages(products);
            //3.给product集合设置 saleCount 和 reviewCount
            for (Product product : products) {
                product.setReviewCount(reviewService.count(product.getId()));
                product.setSaleCount(orderItemService.countByPid(product.getId()));
            }

            //4.根据 sort 给 products 设置排序
            switch (sort) {
                case "review":
                    Collections.sort(products, new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(products, new ProductDateComparator());
                    break;

                case "saleCount":
                    Collections.sort(products, new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(products, new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(products, new ProductAllComparator());
                    break;
            }
            return Result.success(category);
        } else {
            return Result.fail("该类别不存在！");
        }
    }

    @GetMapping("/foresearch")
    public List<Product> foreSearch(@RequestParam("keyword") String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        List<Product> products = productService.list(keyword);
        //给product集合设置 saleCount 和 reviewCount
        for (Product product : products) {
            product.setReviewCount(reviewService.count(product.getId()));
            product.setSaleCount(orderItemService.countByPid(product.getId()));
        }
        productService.setFirstProdutImages(products);

        System.out.println(products);

        return products;
    }

    //立即购买
    @GetMapping("/forebuyone")
    public Object buyOne(@RequestParam("pid") int pid, @RequestParam("num") int num, HttpSession session) {

        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        OrderItem orderItem = new OrderItem(0, pid, -1, userId, num);
        orderItemService.add(orderItem);

        return orderItem;
    }

    //结算
    @GetMapping("/forebuy")
    public Object buy(@RequestParam("oiid") String[] oiids) {
        System.out.println(Arrays.toString(oiids));
        List<OrderItem> orderItems = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        double total = 0;
        for (String oiid : oiids) {
            int id = Integer.parseInt(oiid);
            //根据id获取每个订单项
            OrderItem orderItem = orderItemService.findById(id);
            //根据订单项获取对应的 商品信息
            Product product = productService.findById(orderItem.getPid());
            //给每个商品信息赋值图像
            productService.setFirstProdutImage(product);
            //每个订单项的金额都相加，计算出订单的金额
            total += orderItem.getNumber() * product.getPromotePrice();

            orderItems.add(orderItem);
            products.add(product);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        map.put("products", products);

        return Result.success(map);
    }

    @GetMapping("/foreaddCart")
    public Object addCart(@RequestParam("pid") int pid, @RequestParam("num") int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        //该用户购物车中是否存在当前商品，有的话数量增加，没有的话就新加订单项
        OrderItem orderItem1 = orderItemService.list(userId, pid, -2);
        if (orderItem1 != null) {
            orderItem1.setNumber(orderItem1.getNumber() + num);
            //刷新购物车中的该商品数量
            orderItemService.updateCart(orderItem1.getNumber(), orderItem1.getUid(), orderItem1.getPid());
            return Result.success();
        }

        OrderItem orderItem = new OrderItem(0, pid, -2, userId, num);
        orderItemService.add(orderItem);
        return Result.success();
    }

    @GetMapping("/forecart")
    public Object listCart(@CheckLogin User user, HttpSession session) {
//        User user = (User) session.getAttribute("user");
        System.out.println("user++++++++++"+session.getAttribute("user"));
        System.out.println("userId:++++"+user.getId());
        List<OrderItem> orderItems = orderItemService.list(-2, user.getId());
        List<Product> products = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            //根据 订单项 获取对应的 商品信息
            Product product = productService.findById(orderItem.getPid());
            //给每个商品信息赋值图像
            productService.setFirstProdutImage(product);
            products.add(product);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("products", products);

        System.out.println(orderItems);

        return Result.success(map);
    }

    @GetMapping("/forechangeOrderItem")
    public Object forechangeOrderItem(@CheckLogin User user, @RequestParam("pid") int pid, @RequestParam("num") int num) {

        orderItemService.updateCart(num, user.getId(), pid);

        return Result.success();
    }

    @GetMapping("/foredeleteOrderItem")
    public Object foredeleteOrderItem(HttpServletRequest req, @RequestParam("oiid") int oiid) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.fail("请登录");
        }

        orderItemService.deleteById(oiid);

        return Result.success();
    }

    @PostMapping("/forecreateOrder")
    public Object forecreateOrder(HttpServletRequest req, @RequestBody Order order) throws Exception {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.fail("请登录");
        }

        //订单的创建日期
        Date date = new Date();
        order.setCreateDate(date);
        //订单号
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmss").format(date) + UUID.randomUUID();
        order.setOrderCode(orderCode);
        //用户id
        order.setUid(user.getId());
        //订单状态
        order.setStatus(OrderService.waitPay);

        //支持事务的保存
        float total = orderService.save(order, user);
        HashMap<String, Object> map = new HashMap<>();

        map.put("order", order);
        map.put("total", total);

        return Result.success(map);
    }

    @GetMapping("/forepayed")
    public Object forePayed(@RequestParam("oid") int oid) {

        //支付成功后，修改订单状态为 待发货
        Order order = orderService.payOrder(oid);
        List<OrderItem> orderItems = orderItemService.list(order.getId());
        for (OrderItem orderItem : orderItems) {
            Product product = productService.findById(orderItem.getPid());
            int saleNum = orderItem.getNumber();
            product.setSaleCount(product.getSaleCount()+saleNum);
            productService.update(product);
        }
        return Result.success(order);
    }

    @GetMapping("forebought")
    public Object foreBought(@CheckLogin User user) {

        List<Order> orders = orderService.listByUid(user.getId());
        //给order赋值属性
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemService.list(order.getId());
            //给orderItem赋值属性
            for (OrderItem orderItem : orderItems) {
                Product product = productService.findById(orderItem.getPid());
                productService.setFirstProdutImage(product);
                orderItem.setProduct(product);
            }
            order.setOrderItems(orderItems);

        }

        return Result.success(orders);
    }

    @GetMapping("/foreconfirmPay")
    public Object foreConfirmPay(@RequestParam("oid") int oid) {
        Order order = orderService.findById(oid);

        //填充订单项
        List<OrderItem> orderItems = orderItemService.list(order.getId());
        //给orderItem赋值属性
        for (OrderItem orderItem : orderItems) {
            Product product = productService.findById(orderItem.getPid());
            productService.setFirstProdutImage(product);
            orderItem.setProduct(product);

            order.setOrderItems(orderItems);

        }
        return Result.success(order);
    }

    //确认收货
    @PutMapping("/foreorderConfirmed")
    public Object foreOrderConfirmed(int oid){
        orderService.confirmPay(oid);
        return Result.success();
    }

    //删除订单（标记为delete）
    @PutMapping("/foredeleteOrder")
    public Object foredeleteOrder(int oid){
        orderService.deleteOrder(oid);
        return Result.success();
    }


    @GetMapping("/forereview")
    //订单->订单项->商品 ->图像
    //                ->评价 -> 用户
    public Object foreReview(int oid){
        Order order = orderService.findById(oid);
        //填充订单项
        List<OrderItem> orderItems = orderItemService.list(order.getId());
        //给orderItem赋值属性
        for (OrderItem orderItem : orderItems) {
            Product product = productService.findById(orderItem.getPid());

            //设置评价集合
            List<Review> reviews = reviewService.list(product.getId());
            for (Review review : reviews) {
                review.setUser(userService.findById(review.getUid()));
            }
            product.setReviews(reviews);
            //设置评价数量
            product.setReviewCount(reviewService.count(product.getId()));

            productService.setFirstProdutImage(product);
            orderItem.setProduct(product);

            order.setOrderItems(orderItems);

        }
        return Result.success(order);
    }

    @PostMapping("/foredoreview")
    public Object foredoReview(@CheckLogin User user,@RequestParam("pid") String pid,@RequestParam("content") String content){

        reviewService.add(content, user.getId(), Integer.parseInt(pid));

        return null;
    }
}

