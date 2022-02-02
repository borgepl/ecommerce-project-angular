package com.deborger.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentResult {

    private boolean success;
    private String order_id;
    private String payment_url;
    private String qr_url;
    private String session_id;

    public PaymentResult(boolean success, String order_id, String payment_url, String qr_url, String session_id) {
        this.success = success;
        this.order_id = order_id;
        this.payment_url = payment_url;
        this.qr_url = qr_url;
        this.session_id = session_id;
    }
}
