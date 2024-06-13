package com.diegobonnin.cloudnative.payments.reactive;

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
public class PaymentsRoute {
    
    @Bean
    public RouterFunction<ServerResponse> route(PaymentsHandler paymentsHandler) {

        return RouterFunctions
                .route(GET("/payments").and(accept(MediaType.APPLICATION_JSON)), paymentsHandler::getAllPayments)
                .andRoute(GET("/payments/{id}").and(accept(MediaType.APPLICATION_JSON)), paymentsHandler::getPayment)
                .andRoute(POST("/payments").and(accept(MediaType.APPLICATION_JSON)), paymentsHandler::createPayment)
                .andRoute(PUT("/payments/{id}").and(accept(MediaType.APPLICATION_JSON)), paymentsHandler::updatePayment)
                .andRoute(DELETE("/payments/{id}").and(accept(MediaType.APPLICATION_JSON)), paymentsHandler::deletePayment);

    }

    
}
