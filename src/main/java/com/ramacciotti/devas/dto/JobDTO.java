package com.ramacciotti.devas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private String position;

    private String level;

    private String preference;

    private String objective;

    private BigDecimal expectation;

}
