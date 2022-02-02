package com.deborger.ecommerce.service;

import com.MultiSafepay.classes.GatewayInfo;
import com.MultiSafepay.classes.PaymentOptions;
import com.MultiSafepay.client.MultiSafepayClient;
import com.deborger.ecommerce.dao.CustomerRepository;
import com.deborger.ecommerce.dto.PaymentInfo;
import com.deborger.ecommerce.dto.PaymentOrder;
import com.deborger.ecommerce.dto.Purchase;
import com.deborger.ecommerce.dto.PurchaseResponse;
import com.deborger.ecommerce.entity.Customer;
import com.deborger.ecommerce.entity.Order;
import com.deborger.ecommerce.entity.OrderItem;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Profile("multisafepay")
@Service
public class CheckoutServiceMultiSafePayImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    public CheckoutServiceMultiSafePayImpl(CustomerRepository customerRepository, @Value("${api.key.secret}") String secretKey) {

        this.customerRepository = customerRepository;

        // initialize the MultiSafePay
        MultiSafepayClient.init(true, secretKey);
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // retrieve order_id - create with the paymentOrder
        String orderTrackingNumber = purchase.getOrder_id();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // Check if this is an existing customer
        Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());
        if (customerFromDB != null) {
            // we found them ... assign accordingly
            customer = customerFromDB;
        }
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        return null;
    }

    public JsonObject createPaymentOrder(PaymentOrder paymentOrder) {

        java.util.Date date = new java.util.Date();
        String tempOrderId = Long.toString(date.getTime());

        com.MultiSafepay.classes.Order order = new com.MultiSafepay.classes.Order();
        paymentOrder.setOrder_id(generateOrderTrackingNumber());
        order.setRedirect(paymentOrder.getOrder_id(), paymentOrder.getDescription(),
                paymentOrder.getAmount(), paymentOrder.getCurrency(), new PaymentOptions("http://example.com/notify",
                        "https://localhost:4200/failed",
                        "https://localhost:4200/success"));

        order.customer = new com.MultiSafepay.classes.Customer();

//        order.customer.first_name = "John";
//        order.customer.last_name = "Doe";
//        order.customer.address1 = "Kraanspoor 39";
//        order.customer.zip_code = "1033SC";
//        order.customer.city = "Amsterdam";
//        order.customer.country = "NL";

        order.customer.first_name = paymentOrder.getCust_firstName();
        order.customer.last_name = paymentOrder.getCust_lastName();
        order.customer.address1 = paymentOrder.getCust_address1();
        order.customer.zip_code = paymentOrder.getCust_zipCode();
        order.customer.city = paymentOrder.getCust_city();
        order.customer.country = paymentOrder.getCust_country();

        //order.gateway = "MISTERCASH";
        order.gateway = paymentOrder.getGateway();
        order.gateway_info = new GatewayInfo();
        order.gateway_info.qr_size = 300;
        order.gateway_info.qr_enabled = 1;

        return MultiSafepayClient.createOrder(order);
    }

    @Override
    public String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}
