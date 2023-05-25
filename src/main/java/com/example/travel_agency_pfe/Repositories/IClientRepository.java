package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository<AppUser,String> {
    AppUser getAppUserByUserName(String username);
    @Query("SELECT u FROM AppUser u WHERE u.userName <> 'admin'")
    List<AppUser> getAllUsersExceptAdmin();
}
