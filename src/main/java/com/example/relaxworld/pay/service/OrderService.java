package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.entity.Order;
import com.example.relaxworld.pay.entity.PayUser;


public interface OrderService {
    Order autoOrder(PayUser payUser); // 자동 주문

}
