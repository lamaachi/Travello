package com.example.travel_agency_pfe.Controllers;


import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Services.IClientService;
import com.example.travel_agency_pfe.Services.ITravelService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/panel")
public class SideBarController {

    private IClientService iClientService;
    private ITravelService travelService;

    // clients Menu
    @GetMapping("/admin/clients")   //=>/panel/admin/clients
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String clientList(Model model){

        List<AppUser> users = iClientService.getAllUsers();
        model.addAttribute("users",users);
        return "pages/clients/clientsList";
    }



    // Travels Menu

}
