package com.ramacciotti.devas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramacciotti.devas.enums.Level;
import com.ramacciotti.devas.enums.Objective;
import com.ramacciotti.devas.enums.Preference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String position;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Preference preference;

    @Enumerated(EnumType.STRING)
    private Objective objective;

    private BigDecimal expectation;

}
