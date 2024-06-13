package com.diegobonnin.cloudnative.orders.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("orders")
public class Order {

    @Id
    private Long id;
    private LocalDateTime created;
    private BigDecimal total;
    private String creditcard;
    @Column("product_id")
    private Long productId;
    private Integer quantity;
    @Column("order_status")
    private OrderStatus orderStatus;
    
}
