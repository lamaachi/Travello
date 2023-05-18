package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Invoice;
import com.example.travel_agency_pfe.Models.Reservation;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface IInvoiceService {
    Invoice createNewInvoice(Invoice invoice);
    Invoice findAllByReservation(Reservation reservation);
    void deleteinvoice(Long invoiceid);
}
