package com.deborger.ecommerce.dto;

import com.deborger.ecommerce.entity.Address;
import com.deborger.ecommerce.entity.Customer;
import com.deborger.ecommerce.entity.Order;
import com.deborger.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
