package com.example.travel_agency_pfe.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    @Column(length = 225)
    private String title;
    private int nights ;

    private int days ;

    @NotBlank(message = "Inclusion is required")
    @Column(length = 500)
    private String inclus;
    @NotBlank(message = "Exclusion is required")
    @Column(length = 500)
    private String exclus;


    private double price;
    @NotBlank(message = "Destination is required")
    @Column(length = 225)
    private String destiantion;

    private LocalDate travelDate;



    @Column(length = 64)
    private String image;



//    @Column(name = "SpecilaOffer")
//    private boolean SpecilaOffer = false;


//    public boolean isSpecilaOffer() {
//        return SpecilaOffer;
//    }
//
//    public void setSpecilaOffer(boolean specilaOffer) {
//        SpecilaOffer = specilaOffer;
   // }

    @NotBlank(message = "Travel Type is required")
    private String travelType;

    @NotBlank(message = "Activities are required")
    @Column(length = 1000)
    private String Activities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;



    //Reservations
    @OneToMany(mappedBy = "travel")
    private Set<Reservation> reservations = new HashSet<>();

}
