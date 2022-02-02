package com.deborger.ecommerce.service;

import com.deborger.ecommerce.dto.PaymentInfo;
import com.deborger.ecommerce.dto.PaymentOrder;
import com.deborger.ecommerce.dto.Purchase;
import com.deborger.ecommerce.dto.PurchaseResponse;
import com.google.gson.JsonObject;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

    JsonObject createPaymentOrder(PaymentOrder paymentOrder);

    String generateOrderTrackingNumber();

}
