package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResevationRepository extends JpaRepository<Reservation,Long> {
}
