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
public class Payment{

    private Long id;
    private Long orderId;
    private LocalDateTime received;
    private BigDecimal total;
    private String creditcard;
    private PaymentStatus paymentStatus;

}