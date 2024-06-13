package com.diegobonnin.cloudnative.products.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.diegobonnin.cloudnative.products.domain.Product;
import com.diegobonnin.cloudnative.products.domain.ProductStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test() throws Exception {

        Product p = Product.builder()
                .price(new BigDecimal(10.99d))
                .quantity(100)           
                .build();        


        // create
        Product _p = webTestClient.post().uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(p)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        // retrieve
        webTestClient.get().uri("/products/" + _p.getId())
                .exchange()
                .expectStatus().isOk();

        // update
        _p.setQuantity(2000);

        webTestClient.put().uri("/products/" + _p.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(_p)
                .exchange()
                .expectStatus().isOk();

        // check updated values
        Product updatedProduct = webTestClient.get().uri("/products/" + _p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertEquals(2000, updatedProduct.getQuantity());

        // delete product
        webTestClient.delete().uri("/products/" + _p.getId())
                .exchange()
                .expectStatus().isOk();

        // // test deletion
        // webTestClient.get().uri("/products/" + _p.getId())
        //         .exchange()
        //         .expectStatus().isNotFound();
    }    
    
}
