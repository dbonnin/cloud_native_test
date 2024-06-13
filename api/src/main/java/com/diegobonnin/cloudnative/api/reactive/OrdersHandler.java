package com.diegobonnin.cloudnative.orders.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diegobonnin.cloudnative.api.domain.Order;
import com.diegobonnin.cloudnative.api.domain.OrderStatus;
import com.diegobonnin.cloudnative.api.domain.Payment;
import com.diegobonnin.cloudnative.api.domain.PaymentStatus;
import com.diegobonnin.cloudnative.api.domain.Product;

import reactor.core.publisher.Mono;

@Component
public class OrdersHandler {

    @Value("${orders.url}")
    private String ordersUrl;

    @Value("${payments.url}")
    private String paymentsUrl;

    @Value("${products.url}")
    private String productsUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;


    public OrdersHandler() {
    }

    // implement the logic to create a order
    public Mono<ServerResponse> createOrder(ServerRequest request) {
        return request.bodyToMono(Order.class)
        .flatMap(this::callOrdersService)
        .flatMap(order -> {
            if (order.getOrderStatus() == OrderStatus.CREATED) {
                return callPaymentsService(order)
                        .flatMap(payment -> {
                            if (payment.getPaymentStatus() == PaymentStatus.ACCEPTED) {
                                return callProductsService(order);
                            } else {
                                return Mono.just(new Product());  // Return empty product or handle as needed
                            }
                        });
            } else {
                return Mono.just(new Product());  // Return empty product or handle as needed
            }
        })
        .flatMap(finalResponse -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(finalResponse)));        

    
    }

    private Mono<Order> callOrdersService(Order order) {
        return webClientBuilder.build()
                .post()
                .uri(ordersUrl)
                .body(BodyInserters.fromValue(order))
                .retrieve()
                .bodyToMono(Order.class);
    }

    private Mono<Payment> callPaymentsService(Order order) {

        Payment payment = new Payment();
        payment.setTotal(order.getTotal());
        payment.setCreditcard(order.getCreditcard());

        return webClientBuilder.build()
                .post()
                .uri(paymentsUrl)
                .body(BodyInserters.fromValue(payment))  // Pass the product to service2
                .retrieve()
                .bodyToMono(Payment.class);
    }

    private Mono<Product> callProductsService(Order order) {

        Product product = new Product();
        product.setId(Long.valueOf(order.getProductId()));
        product.setQuantity(order.getQuantity());

        return webClientBuilder.build()
                .post()
                .uri(productsUrl)
                .body(BodyInserters.fromValue(product))  // Pass the payment to service3
                .retrieve()
                .bodyToMono(Product.class);
    }    



    
}
