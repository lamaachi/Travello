package com.example.travel_agency_pfe.Configurations;

import com.example.travel_agency_pfe.Models.Agency;
import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Repositories.IAgencyRepositry;
import com.example.travel_agency_pfe.Repositories.IAppRoleRepository;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultAgencyInitializer {

    @Autowired
    private IAgencyRepositry agencyRepositry;

    @Autowired
    private IAppUserRepository appUserRepository;

    @Autowired
    private IAppRoleRepository appRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initDefaultAgency();
        initDefaultAdmin();
    }

    public void initDefaultAgency() {
        // Check if a default agency already exists
        if (agencyRepositry.count() == 0) {
            Agency defaultAgency = new Agency();
            defaultAgency.setName("Default Agency");
            defaultAgency.setPhone("123456789");
            defaultAgency.setAdress("Default Address");
            defaultAgency.setBank("Default Bank");
            agencyRepositry.save(defaultAgency);
        }
    }

    public void initDefaultAdmin() {
        System.out.println("Initializing default admin user...");
        // Create roles if they don't exist
        if (appRoleRepository.count() == 0) {
            appRoleRepository.save(new AppRole("ADMIN"));
            appRoleRepository.save(new AppRole("USER"));
        }

        // Check if a default admin already exists
        if (!appUserRepository.existsByUserName("admin")) {
            AppUser admin = new AppUser();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setCIN("admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@admin.com");
            admin.setAdress("admin");
            admin.setPhone("admin");
            admin.setIsadmin(true);
            
            List<AppRole> roles = new ArrayList<>();
            roles.add(appRoleRepository.findById("ADMIN").get());
            admin.setRoles(roles);

            appUserRepository.save(admin);
        }
    }
}
