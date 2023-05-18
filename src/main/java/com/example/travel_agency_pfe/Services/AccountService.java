package com.example.travel_agency_pfe.Services;


import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;
import jakarta.transaction.Transactional;


public interface AccountService {

    void addNewUser(String username, String firstn, String lastn, String email, String password, String phone, String adress,String cin);

    AppRole addNewRole(String role);

    AppUser loadUserByUsername(String username);

    void addRoleToUser(String username, String role);
    AppRole getAppRoleByRole(String role);
    void removeRoleFromUser(String username, String role);


     boolean existsByUsernameOrEmail(String username,String email);




}