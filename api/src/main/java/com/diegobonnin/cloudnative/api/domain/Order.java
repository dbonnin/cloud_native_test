package com.diegobonnin.cloudnative.api.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private Long id;
    private LocalDateTime created;
    private BigDecimal total;
    private String creditcard;
    private String productId;
    private Integer quantity;
    private OrderStatus orderStatus;
    
}
