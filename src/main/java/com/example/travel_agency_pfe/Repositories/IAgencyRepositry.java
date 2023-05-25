package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.Agency;
import com.example.travel_agency_pfe.Models.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgencyRepositry extends JpaRepository<Agency,Long> {
    Agency findFirstByOrderByName();
}
