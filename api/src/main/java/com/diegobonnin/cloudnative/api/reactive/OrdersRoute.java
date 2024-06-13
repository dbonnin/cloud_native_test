package com.diegobonnin.cloudnative.orders.reactive;


import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OrdersRoute {
    
    @Bean
    public RouterFunction<ServerResponse> route(OrdersHandler ordersHandler) {

        // implements all CRUD operations, use the id as the path variable
        return RouterFunctions
                .route(POST("/api/orders").and(accept(MediaType.APPLICATION_JSON)), ordersHandler::createOrder);

    }

    
}
