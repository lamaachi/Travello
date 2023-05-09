package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<AppUser,String> {
}
