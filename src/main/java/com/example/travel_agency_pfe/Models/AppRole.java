package com.example.travel_agency_pfe.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name = "roles")
public class AppRole {
    @Id
    private String role;

}
