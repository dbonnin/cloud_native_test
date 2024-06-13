package com.diegobonnin.cloudnative.products.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diegobonnin.cloudnative.products.domain.Product;
import com.diegobonnin.cloudnative.products.persistence.ProductsRepository;

import reactor.core.publisher.Mono;

@Component
public class ProductsHandler {

    private final ProductsRepository productsRepository;

    public ProductsHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok().body(productsRepository.findAll(), Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        return ServerResponse.ok().body(productsRepository.findById(Long.parseLong(request.pathVariable("id"))), Product.class);
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
            .flatMap(productsRepository::save)
            .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
            .flatMap(product -> productsRepository.findById(Long.parseLong(request.pathVariable("id")))
                .flatMap(existingProduct -> {
                    if (product.getPrice() != null) {
                        existingProduct.setPrice(product.getPrice());
                    }
                    if (product.getQuantity() != null) {
                        existingProduct.setQuantity(product.getQuantity());
                    }
                    return productsRepository.save(existingProduct);
                })
            )
            .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return productsRepository.findById(Long.parseLong(request.pathVariable("id")))
            .flatMap(product -> productsRepository.delete(product)
                .then(ServerResponse.ok().build())
            );
    }    


    
}
