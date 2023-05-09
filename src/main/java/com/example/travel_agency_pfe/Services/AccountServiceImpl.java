package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Repositories.IAppRoleRepository;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@Data
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {


    private IAppRoleRepository iAppRoleRepository;
    private IAppUserRepository iAppUserRepository;

    private PasswordEncoder passwordEncoder;

    //add new user method
    @Override
    public void addNewUser(String username, String firstn, String lastn, String email, String password, String phone, String adress) {
        AppUser appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .userName(username)
                .firstName(firstn)
                .lastName(lastn)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .adress(adress)
                .build();
        iAppUserRepository.save(appUser);
    }


    //add new Role method
    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = iAppRoleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("this Role alredy exist!!!");

        //creat New App User

        appRole = AppRole.builder()
                .role(role)
                .build();
        return iAppRoleRepository.save(appRole);
    }


    @Override
    public AppUser loadUserByUsername(String username) {
        return  iAppUserRepository.findByUserName(username);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = iAppUserRepository.findByUserName(username);
        Optional<AppRole> appRole =  iAppRoleRepository.findById(role);
        AppRole savedRole = appRole.orElseThrow(() -> new RuntimeException("Role not found"));
        appUser.getRoles().add(savedRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = iAppUserRepository.findByUserName(username);
        Optional<AppRole> appRole =  iAppRoleRepository.findById(role);
        AppRole savedRole = appRole.orElseThrow(() -> new RuntimeException("Role not found"));

        appUser.getRoles().remove(savedRole);
    }
}
