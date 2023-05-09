package com.example.travel_agency_pfe.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SecondaryTables;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter @Setter
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Subscriber {
    @Id
    private String email;

}
