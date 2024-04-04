package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.entity.Form;
import com.example.relaxworld.pay.entity.PayUser;


public interface OrderService {
    Form autoOrder(PayUser payUser); // 자동 주문

}
