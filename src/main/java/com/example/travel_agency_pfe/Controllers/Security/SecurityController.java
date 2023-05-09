package com.example.travel_agency_pfe.Controllers.Security;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class SecurityController {
    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "pages/errors/_403";
    }


    @GetMapping("")
    public String notFound(){
        return "pages/errors/_404";
    }
}
