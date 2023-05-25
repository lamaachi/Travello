package com.example.travel_agency_pfe.Controllers;


import com.example.travel_agency_pfe.Models.Agency;
import com.example.travel_agency_pfe.Repositories.IAgencyRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AgencyController {
    @Autowired
    private IAgencyRepositry agencyRepositry;

    @PostMapping("/Auth/profile/updateAgency")
    public String updateAgency(@ModelAttribute("agency") Agency updatedAgency, @RequestParam("agencyid") Long agencyid) {
        System.out.println("=========================================================="+updatedAgency);
        Agency existingAgency = agencyRepositry.findById(agencyid).orElse(null);
        if (existingAgency != null) {
            // Update the agency information with the new values
            existingAgency.setName(updatedAgency.getName());
            existingAgency.setAdress(updatedAgency.getAdress());
            existingAgency.setBank(updatedAgency.getBank());
            existingAgency.setPhone(updatedAgency.getPhone());
            // Save the updated agency
            agencyRepositry.save(existingAgency);
        }
        return "redirect:/Auth/profile";
    }
}
