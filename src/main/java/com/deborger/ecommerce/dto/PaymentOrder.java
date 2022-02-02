package com.deborger.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentOrder {

    private String order_id;
    private String description;
    private String gateway;
    private int amount;
    private String currency;
    private String receiptEmail;
    private String cust_firstName;
    private String cust_lastName;
    private String cust_address1;
    private String cust_zipCode;
    private String cust_city;
    private String cust_country;

}
