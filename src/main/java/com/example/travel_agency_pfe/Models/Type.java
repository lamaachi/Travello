package com.example.travel_agency_pfe.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typename;


}
