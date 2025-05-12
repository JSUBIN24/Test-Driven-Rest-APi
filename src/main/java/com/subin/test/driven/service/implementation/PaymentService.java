/*
package com.subin.test.driven.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/// This is the code I need to find what is wrong in this code.
@Service
public class PaymentService {

    public void process(Payment payment) {
        pay(payment.amount(), payment.account());
    }

    // @Transactional
    private void pay(Object amount, Object account) {

    }

}

record Payment(Object amount, Object account) {
}

@RestController
class CustomerController {

    private final PaymentService paymentService;

    public CustomerController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/users/payment")
    public void getPaymentDetails(@RequestBody Payment payment) {
        paymentService.process(payment);
    }
}
*/
