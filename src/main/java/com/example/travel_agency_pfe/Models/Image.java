package com.example.travel_agency_pfe.Models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

//    @ManyToOne
//    private Travel travel;

}
