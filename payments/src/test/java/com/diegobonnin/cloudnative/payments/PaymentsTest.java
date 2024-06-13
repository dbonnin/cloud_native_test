package com.diegobonnin.cloudnative.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.diegobonnin.cloudnative.payments.domain.Payment;
import com.diegobonnin.cloudnative.payments.domain.PaymentStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentsTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test() throws Exception {

        Payment p = Payment.builder()
                .total(new BigDecimal(10.99d))
                .paymentStatus(PaymentStatus.NEW)
                .orderId(1l)
                .creditcard("1234-1234-1234-1234")
                .received(LocalDateTime.now())                
                .build();        


        // create
        Payment _p = webTestClient.post().uri("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(p)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Payment.class)
                .returnResult()
                .getResponseBody();

        // retrieve
        webTestClient.get().uri("/payments/" + _p.getId())
                .exchange()
                .expectStatus().isOk();

        // update
        _p.setPaymentStatus(PaymentStatus.ACCEPTED);

        webTestClient.put().uri("/payments/" + _p.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(_p)
                .exchange()
                .expectStatus().isOk();

        // check updated values
        Payment updatedPayment = webTestClient.get().uri("/payments/" + _p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Payment.class)
                .returnResult()
                .getResponseBody();

        assertEquals(PaymentStatus.ACCEPTED, updatedPayment.getPaymentStatus());

        // delete payment
        webTestClient.delete().uri("/payments/" + _p.getId())
                .exchange()
                .expectStatus().isOk();

        // test deletion
        // webTestClient.get().uri("/payments/" + _p.getId())
        //         .exchange()
        //         .expectStatus().isNotFound();
    }    
    
}
