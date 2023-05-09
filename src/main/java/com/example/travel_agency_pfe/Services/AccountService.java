package com.example.travel_agency_pfe.Services;


import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;

public interface AccountService {

    void addNewUser(String username, String firstn, String lastn, String email, String password, String phone, String adress);

    AppRole addNewRole(String role);

    AppUser loadUserByUsername(String username);

    void addRoleToUser(String username, String role);

    void removeRoleFromUser(String username, String role);

}