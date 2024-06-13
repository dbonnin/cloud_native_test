package com.diegobonnin.cloudnative.orders.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diegobonnin.cloudnative.orders.domain.Order;
import com.diegobonnin.cloudnative.orders.persistence.OrderRepository;

import reactor.core.publisher.Mono;

@Component
public class OrdersHandler {

    private final OrderRepository orderRepository;

    public OrdersHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    
    public Mono<ServerResponse> getAllOrders(ServerRequest request) {
        return ServerResponse.ok().body(orderRepository.findAll(), Order.class);
    }

    public Mono<ServerResponse> getOrder(ServerRequest request) {
        return ServerResponse.ok().body(orderRepository.findById(Long.parseLong(request.pathVariable("id"))), Order.class);
    }

    public Mono<ServerResponse> createOrder(ServerRequest request) {
        return request.bodyToMono(Order.class)
            .flatMap(orderRepository::save)
            .flatMap(order -> ServerResponse.ok().bodyValue(order));
    }

    public Mono<ServerResponse> updateOrder(ServerRequest request) {
        return request.bodyToMono(Order.class)
            .flatMap(order -> orderRepository.findById(Long.parseLong(request.pathVariable("id")))
                .flatMap(existingOrder -> {
                    if (order.getCreated() != null) {
                        existingOrder.setCreated(order.getCreated());
                    }
                    if (order.getTotal() != null) {
                        existingOrder.setTotal(order.getTotal());
                    }
                    if (order.getCreditcard() != null) {
                        existingOrder.setCreditcard(order.getCreditcard());
                    }
                    if (order.getProductId() != null) {
                        existingOrder.setProductId(order.getProductId());
                    }
                    if (order.getQuantity() != null) {
                        existingOrder.setQuantity(order.getQuantity());
                    }
                    if (order.getOrderStatus() != null) {
                        existingOrder.setOrderStatus(order.getOrderStatus());
                    }                    
                    return orderRepository.save(existingOrder);
                })
            )
            .flatMap(order -> ServerResponse.ok().bodyValue(order));
    }

    public Mono<ServerResponse> deleteOrder(ServerRequest request) {
        return orderRepository.findById(Long.parseLong(request.pathVariable("id")))
            .flatMap(order -> orderRepository.delete(order)
                .then(ServerResponse.ok().build())
            );
    }    


    
}
