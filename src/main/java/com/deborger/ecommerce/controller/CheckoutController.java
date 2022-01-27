package com.deborger.ecommerce.controller;

import com.deborger.ecommerce.dto.PaymentInfo;
import com.deborger.ecommerce.dto.Purchase;
import com.deborger.ecommerce.dto.PurchaseResponse;
import com.deborger.ecommerce.service.CheckoutService;
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

}

