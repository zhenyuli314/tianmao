package com.lzy.tianmao.web;

import com.lzy.tianmao.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {

    @GetMapping("/")
    public String index(){
        return "redirect:home";
    }
    @GetMapping("/home")
    public String foreHome(){
        return "fore/home";
    }

    @GetMapping("/register")
    public String register(){
        return "fore/register";
    }

    @GetMapping("/registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    @GetMapping("/login")
    public String login(){
        return "fore/login";
    }

    @GetMapping("/forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/product")
    public String product(){
        return "fore/product";
    }

    @GetMapping("/category")
    public String category(){
        return "fore/category";
    }

    @GetMapping("/search")
    public String searchResult(){
        return "fore/searchResult";
    }

    @GetMapping("/buy")
    public String buy(){
        return "fore/buy";
    }

    @GetMapping("/cart")
    public String cart(){
        return "fore/cart";
    }

    @GetMapping("/alipay")
    public String pay(){
        return "fore/alipay";
    }

    @GetMapping("/payed")
    public String payed(){
        return "fore/payed";
    }

    @GetMapping("/bought")
    public String bought(){
        return "fore/bought";
    }

    @GetMapping("/confirmPay")
    public String confirmPay(){
        return "fore/confirmPay";
    }

    @GetMapping("/orderConfirmed")
    public String orderConfirmed(){
        return "fore/orderConfirmed";
    }

    @GetMapping("/review")
    public String review(){
        return "fore/review";
    }
}
