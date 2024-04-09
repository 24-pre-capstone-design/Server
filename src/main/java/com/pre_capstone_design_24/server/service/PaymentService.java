package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Payment;
import com.pre_capstone_design_24.server.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Long createPayment() {
        Payment newPayment = Payment.builder().build();
        return save(newPayment).getId();
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

}
