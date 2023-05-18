package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.DTO.TotalAmountPerMonthDto;
import com.example.travel_agency_pfe.Models.Invoice;
import com.example.travel_agency_pfe.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice,Long> {
    Invoice  getInvoiceByReservation(Reservation reservation);

    @Query("SELECT MONTH(i.date) AS month, SUM(i.amount) AS totalAmount " +
            "FROM Invoice i " +
            "WHERE YEAR(i.date) = :year " +
            "GROUP BY MONTH(i.date) " +
            "ORDER BY MONTH(i.date)")
    List<Object[]> getTotalAmountPerMonth(@Param("year") int year);

}
