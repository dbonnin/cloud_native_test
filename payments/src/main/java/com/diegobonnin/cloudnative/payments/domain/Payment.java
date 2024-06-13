package com.diegobonnin.cloudnative.payments.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
@Table("payments")
public class Payment{

    @Id
    private Long id;
    private LocalDateTime received;
    private BigDecimal total;
    private String creditcard;
    @Column("order_id")
    private Long orderId;
    @Column("payment_status")
    private PaymentStatus paymentStatus;

}