package com.diegobonnin.cloudnative.products.reactive;


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
public class ProductsRoute {
    
    @Bean
    public RouterFunction<ServerResponse> route(ProductsHandler productsHandler) {

        return RouterFunctions
                .route(GET("/products").and(accept(MediaType.APPLICATION_JSON)), productsHandler::getAllProducts)
                .andRoute(GET("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productsHandler::getProduct)
                .andRoute(POST("/products").and(accept(MediaType.APPLICATION_JSON)), productsHandler::createProduct)
                .andRoute(PUT("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productsHandler::updateProduct)
                .andRoute(DELETE("/products/{id}").and(accept(MediaType.APPLICATION_JSON)), productsHandler::deleteProduct);

    }

    
}
