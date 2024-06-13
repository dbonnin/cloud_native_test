package com.diegobonnin.cloudnative.payments.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


import com.diegobonnin.cloudnative.payments.domain.Payment;
import com.diegobonnin.cloudnative.payments.domain.PaymentStatus;
import reactor.core.publisher.Flux;

@Repository 
public interface PaymentsRepository extends  ReactiveCrudRepository<Payment, Long> {

    Flux<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

}