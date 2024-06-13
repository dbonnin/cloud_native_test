package com.diegobonnin.cloudnative.orders.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.diegobonnin.cloudnative.orders.domain.Order;
import com.diegobonnin.cloudnative.orders.domain.OrderStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test() throws Exception {

        Order p = Order.builder()
                .total(new BigDecimal(10.99d))
                .orderStatus(OrderStatus.PENDING)
                .productId("1")
                .quantity(1)
                .creditcard("1234-1234-1234-1234")
                .created(LocalDateTime.now())                
                .build();        


        // create
        Order _p = webTestClient.post().uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(p)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Order.class)
                .returnResult()
                .getResponseBody();

        // retrieve
        webTestClient.get().uri("/api/orders/" + _p.getId())
                .exchange()
                .expectStatus().isOk();

        // update
        _p.setOrderStatus(OrderStatus.CANCELED);

        webTestClient.put().uri("/api/orders/" + _p.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(_p)
                .exchange()
                .expectStatus().isOk();

        // check updated values
        Order updatedOrder = webTestClient.get().uri("/api/orders/" + _p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Order.class)
                .returnResult()
                .getResponseBody();

        assertEquals(OrderStatus.CANCELED, updatedOrder.getOrderStatus());

        // delete order
        webTestClient.delete().uri("/api/orders/" + _p.getId())
                .exchange()
                .expectStatus().isOk();

        // test deletion
        webTestClient.get().uri("/api/orders/" + _p.getId())
                .exchange()
                .expectStatus().isNotFound();
    }    
    
}
