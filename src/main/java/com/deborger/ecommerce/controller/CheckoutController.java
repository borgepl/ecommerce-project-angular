package com.deborger.ecommerce.controller;

import com.MultiSafepay.client.MultiSafepayClient;
import com.deborger.ecommerce.dto.*;
import com.deborger.ecommerce.service.CheckoutService;
import com.google.gson.JsonObject;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

// @CrossOrigin
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/uuid")
    public PurchaseResponse generateOrderTrackingNumber() {
        return new PurchaseResponse(checkoutService.generateOrderTrackingNumber());
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        return checkoutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {

        logger.info("PaymentInfo amount : " + paymentInfo.getAmount());
        logger.info("PaymentInfo currency : " + paymentInfo.getCurrency());

        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/payment-order")
    public PaymentResult createPaymentOrder(@RequestBody PaymentOrder paymentOrder) {

        logger.info("Payment amount : " + paymentOrder.getAmount());
        logger.info("Payment currency : " + paymentOrder.getCurrency());

        JsonObject jsonResponse = checkoutService.createPaymentOrder(paymentOrder);

        // extract data from jsonResponse
        String jsonStr = jsonResponse.toString();
        boolean success = jsonResponse.get("success").getAsBoolean();
        JsonObject data = jsonResponse.get("data").getAsJsonObject();
        String payment_OrderId = data.get("order_id").toString();
        String payment_url = data.get("payment_url").toString();
        String session_id = data.get("session_id").toString();
        String qr_url = MultiSafepayClient.getQrUrl(jsonResponse);

        // create PaymentResult
        PaymentResult paymentResult = new PaymentResult(success,payment_OrderId,payment_url, qr_url, session_id);

        logger.info("JsonRespone : " + jsonStr);
        logger.info("Payment Order created : " + success + " " + payment_OrderId + " " + qr_url);

        return paymentResult;
    }

}

