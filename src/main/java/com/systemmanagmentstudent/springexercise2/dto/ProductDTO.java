package com.systemmanagmentstudent.springexercise2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
