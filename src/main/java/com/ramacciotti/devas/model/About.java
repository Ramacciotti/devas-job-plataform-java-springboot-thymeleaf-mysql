package com.ramacciotti.devas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "about")
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Long age;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Column(nullable = false, length = 3000)
    private String description;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] photo;

}
