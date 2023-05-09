package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IClientService {
    List<AppUser> getAllUsers();
    void deleteClient(String id);
    AppUser save(AppUser appUser);

    AppUser getById(String id);

}
