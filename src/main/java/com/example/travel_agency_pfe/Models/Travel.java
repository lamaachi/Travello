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
    private double pricechuild;
    @NotBlank(message = "Destination is required")
    @Column(length = 225)
    private String destiantion;



    private LocalDate travelDate;


//    @Column(length = 64)
//    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

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
    @Column(columnDefinition = "TEXT")
    private String Activities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    //Reservations
    @OneToMany(mappedBy = "travel",cascade = CascadeType.REMOVE)
    private Set<Reservation> reservations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travel travel = (Travel) o;
        return nights == travel.nights && days == travel.days && Double.compare(travel.price, price) == 0 && Double.compare(travel.pricechuild, pricechuild) == 0 && Objects.equals(id, travel.id) && Objects.equals(title, travel.title) && Objects.equals(inclus, travel.inclus) && Objects.equals(exclus, travel.exclus) && Objects.equals(destiantion, travel.destiantion) && Objects.equals(travelDate, travel.travelDate) && Objects.equals(image, travel.image) && Objects.equals(createdAt, travel.createdAt) && Objects.equals(updatedAt, travel.updatedAt) && Objects.equals(specialOffer, travel.specialOffer) && Objects.equals(travelType, travel.travelType) && Objects.equals(Activities, travel.Activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, nights, days, inclus, exclus, price, pricechuild, destiantion, travelDate, image, createdAt, updatedAt, specialOffer, travelType, Activities);
    }

    @Override
    public String toString() {
        return "AppUser";
    }

}
