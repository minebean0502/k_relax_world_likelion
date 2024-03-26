package com.example.relaxworld.service;

import com.example.relaxworld.entity.Order;
import com.example.relaxworld.entity.User;


public interface OrderService {
    Order autoOrder(User user); // 자동 주문

}
