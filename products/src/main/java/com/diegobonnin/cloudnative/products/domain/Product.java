package com.diegobonnin.cloudnative.products.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("products")
public class Product {

    @Id
    private Long id;
    private BigDecimal price;
    private Integer quantity;
    
}
