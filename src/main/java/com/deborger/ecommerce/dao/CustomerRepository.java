package com.deborger.ecommerce.dao;

import com.deborger.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // @Query("select c from Customer c where c.email = ?1")
    Customer findByEmail(String theEmail);
}