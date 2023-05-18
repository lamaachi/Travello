package com.example.travel_agency_pfe.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "TotalAmount")
    private double TotalAmount;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int numberOfAdults;

    @Column(nullable = false)
    private int numberOfChildren;
    @Column(name = "payed")
    private Boolean payed;


    @Column(name = "invoiced")
    private Boolean invoiced;
    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    //invoice
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.REMOVE)
    private Invoice invoice;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", TotalAmount=" + TotalAmount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", numberOfAdults=" + numberOfAdults +
                ", numberOfChildren=" + numberOfChildren +
                ", payed=" + payed +
                ", invoiced=" + invoiced +
                ", travel=" + travel +
                ", appUser=" + appUser +
                '}';
    }
}
