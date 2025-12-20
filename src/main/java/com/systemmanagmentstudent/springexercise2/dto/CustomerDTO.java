package com.systemmanagmentstudent.springexercise2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    private String name;
    private Integer age;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
