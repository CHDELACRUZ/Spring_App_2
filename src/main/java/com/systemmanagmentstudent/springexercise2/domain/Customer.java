package com.systemmanagmentstudent.springexercise2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can't be null")
    @Size(min = 4, max = 10)
    private String name;

    @NotNull(message = "Age can't be null")
    @Size(min = 1, max = 100, message = "Age must be between 1 and 100")
    private Integer age;

    @NotBlank(message = "Address can't be null")
    private String address;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    @UpdateTimestamp
    private LocalDateTime updated_At;

}
