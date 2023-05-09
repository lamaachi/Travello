package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Services.ITravelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.Flow;

@AllArgsConstructor
@Controller
public class HomeController{
    private ITravelService travelService;
    @GetMapping("/")
    public String index(Model model){
        List<Travel> travelList = travelService.getAllTravels();
        Subscriber subscriber = new Subscriber();
        model.addAttribute("travels",travelList);
        model.addAttribute("subscriber",subscriber);
        return "index";
    }
}
