package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUserName(String username);
}
