package com.example.travel_agency_pfe.Configurations;


import com.example.travel_agency_pfe.Models.Agency;
import com.example.travel_agency_pfe.Repositories.IAgencyRepositry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultAgencyInitializer {

    @Autowired
    private IAgencyRepositry agencyRepositry;
    @PostConstruct
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
}
