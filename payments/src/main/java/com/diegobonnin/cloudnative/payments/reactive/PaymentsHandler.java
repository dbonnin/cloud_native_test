package com.diegobonnin.cloudnative.payments.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diegobonnin.cloudnative.payments.domain.Payment;
import com.diegobonnin.cloudnative.payments.persistence.PaymentsRepository;

import reactor.core.publisher.Mono;

@Component
public class PaymentsHandler {

    private final PaymentsRepository paymentsRepository;

    public PaymentsHandler(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    // implement the logic to get all payments
    public Mono<ServerResponse> getAllPayments(ServerRequest request) {
        return ServerResponse.ok().body(paymentsRepository.findAll(), Payment.class);
    }
    // implement the logic to get a payment by id
    public Mono<ServerResponse> getPayment(ServerRequest request) {
        return ServerResponse.ok().body(paymentsRepository.findById(Long.parseLong(request.pathVariable("id"))), Payment.class);
    }

    // implement the logic to create a payment
    public Mono<ServerResponse> createPayment(ServerRequest request) {
        return request.bodyToMono(Payment.class)
            .flatMap(paymentsRepository::save)
            .flatMap(payment -> ServerResponse.ok().bodyValue(payment));
    }

    // implement the logic to update a payment, using the id from the path, and the payment from the body, and returning the updated payment, and only update the fields that are not null
    public Mono<ServerResponse> updatePayment(ServerRequest request) {
        return request.bodyToMono(Payment.class)
            .flatMap(payment -> paymentsRepository.findById(Long.parseLong(request.pathVariable("id")))
                .flatMap(existingPayment -> {
                    if (payment.getReceived() != null) {
                        existingPayment.setReceived(payment.getReceived());
                    }
                    if (payment.getTotal() != null) {
                        existingPayment.setTotal(payment.getTotal());
                    }
                    if (payment.getCreditcard() != null) {
                        existingPayment.setCreditcard(payment.getCreditcard());
                    }
                    if (payment.getPaymentStatus() != null) {
                        existingPayment.setPaymentStatus(payment.getPaymentStatus());
                    }                  
                    return paymentsRepository.save(existingPayment);
                })
            )
            .flatMap(payment -> ServerResponse.ok().bodyValue(payment));
    }

    // implement the logic to delete a payment by id
    public Mono<ServerResponse> deletePayment(ServerRequest request) {
        return paymentsRepository.findById(Long.parseLong(request.pathVariable("id")))
            .flatMap(payment -> paymentsRepository.delete(payment)
                .then(ServerResponse.ok().build())
            );
    }    


    
}
