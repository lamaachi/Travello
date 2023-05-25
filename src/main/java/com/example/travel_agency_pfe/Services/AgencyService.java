package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Repositories.IAgencyRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {
    @Autowired
    private IAgencyRepositry agencyRepositry;


}
