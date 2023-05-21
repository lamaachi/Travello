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
import java.util.Objects;

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
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Invoice invoice;

    @Override
    public String toString() {
        return "Reservation";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Double.compare(that.TotalAmount, TotalAmount) == 0 && numberOfAdults == that.numberOfAdults && numberOfChildren == that.numberOfChildren && Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(payed, that.payed) && Objects.equals(invoiced, that.invoiced);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, TotalAmount, createdAt, updatedAt, numberOfAdults, numberOfChildren, payed, invoiced);
    }
}
