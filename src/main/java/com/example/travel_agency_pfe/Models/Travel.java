package com.example.travel_agency_pfe.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @Column(name = "special_offer")
    private Boolean specialOffer;

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


    @Override
    public String toString() {
        return "AppUser";
    }

}
