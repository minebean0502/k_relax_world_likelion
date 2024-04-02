package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.entity.Order;
import com.example.relaxworld.pay.entity.User;


public interface OrderService {
    Order autoOrder(User user); // 자동 주문
}
