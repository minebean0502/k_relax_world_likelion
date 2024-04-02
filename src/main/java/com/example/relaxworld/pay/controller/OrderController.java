package com.example.relaxworld.pay.controller;

import com.example.relaxworld.pay.entity.Order;
import com.example.relaxworld.pay.entity.User;
import com.example.relaxworld.pay.service.OrderService;
import com.example.relaxworld.pay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    @GetMapping("/order")
    public String order(@RequestParam(name = "message", required = false) String message,
                        @RequestParam(name = "orderUid", required = false) String id,
                        Model model) {
        model.addAttribute("message", message);
        model.addAttribute("orderUid", id);
        return "order";
    }
    @PostMapping("/order")
    public String autoOrder() {
        User user = userService.autoRegister();
        Order order = orderService.autoOrder(user);
        String message = "주문 실패";
        if(order != null) {
            message = "주문 성공";
        }
        String encode = URLEncoder.encode(message, StandardCharsets.UTF_8);
        return "redirect:/order?message="+encode+"&orderUid="+order.getOrderUid();
    }
}
