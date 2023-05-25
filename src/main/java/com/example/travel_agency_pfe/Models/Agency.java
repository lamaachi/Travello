package com.example.travel_agency_pfe.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Entity
public class Agency {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotBlank
    private String Phone;
    @NotBlank
    private String adress;
    @NotBlank
    private String bank;
}
