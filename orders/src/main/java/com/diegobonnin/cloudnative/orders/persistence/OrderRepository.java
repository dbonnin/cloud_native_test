package com.diegobonnin.cloudnative.orders.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.diegobonnin.cloudnative.orders.domain.Order;
import com.diegobonnin.cloudnative.orders.domain.OrderStatus;
import reactor.core.publisher.Flux;

@Repository 
public interface OrderRepository extends  ReactiveCrudRepository<Order, Long> {

    Flux<Order> findByOrderStatus(OrderStatus orderStatus);

}