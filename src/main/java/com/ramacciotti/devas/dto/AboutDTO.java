package com.ramacciotti.devas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutDTO {

    private String name;

    private Long age;

    private String city;

    private String description;

    private MultipartFile photo;

}
