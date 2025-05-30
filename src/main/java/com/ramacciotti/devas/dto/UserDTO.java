package com.ramacciotti.devas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private AboutDTO about;

    private SocialDTO social;

    private JobDTO job;

    private String technology;

}
