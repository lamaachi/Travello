package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Reservation;
import com.example.travel_agency_pfe.Models.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface IReservationService {
    Reservation save(Reservation reservation);
    List<Reservation> getAllReservations();

    List<Reservation> getReservationsByUser(String username);

    void deleteRes(Long id);
    Optional<Reservation> getReservationById(Long id);
}
