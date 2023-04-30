package com.example.travel_agency_pfe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{

    @GetMapping("/index")
    public String index(){ return "auth-login"; }


    @GetMapping("/dashboard")
    public String dashboard(){ return "pages/landing_page"; }

}
