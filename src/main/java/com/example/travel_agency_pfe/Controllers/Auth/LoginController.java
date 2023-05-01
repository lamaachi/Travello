package com.example.travel_agency_pfe.Controllers.Auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @GetMapping("/Auth/login")
    public String loginPage(){
        return "pages/auth/auth-login";
    }

    @RequestMapping("/adminpanel")
    public String adminPanel(){
        return "landing_page";
    }

    @GetMapping("/Auth/register")
    public String registerPage(){
        return "pages/auth/auth-register";
    }

    @GetMapping("/Auth/getpassword")
    public String forgotPassPage(){
        return "pages/auth/auth-forgot-password";
    }
}
