package com.example.travel_agency_pfe.Controllers.Auth;

import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Services.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private AccountServiceImpl accountService;
    private IAppUserRepository iAppUserRepository;
    @GetMapping("/login")
    public String loginPage(){

        return "pages/auth/auth-login";
    }


    @GetMapping("/panel")
    public String adminPanel(){
        return "landing_page";
    }

    @GetMapping("/Auth/register")
    public String registerPage(Model model){
        model.addAttribute("appUser", new AppUser());
        return "pages/auth/auth-register";
    }
    @PostMapping("/Auth/register")
    public String processRegistrationForm(@ModelAttribute("appUser") @Valid AppUser appUser,
                                          BindingResult result, Model model,RedirectAttributes ra) {

        if (result.hasErrors()) {
            // Add error messages to the Thymeleaf model
            model.addAttribute("errors", result.getAllErrors());
            return "pages/auth/auth-register";
        }
        ra.addAttribute("message","Your Account Has Been Created,Please Login");
        accountService.addNewUser(
                appUser.getUserName(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getPassword(),
                appUser.getPhone(),
                appUser.getAdress()
        );

        accountService.addRoleToUser(appUser.getUserName(),"USER");
        return "redirect:/login";
    }
    @GetMapping("/Auth/getpassword")
    public String forgotPassPage(){
        return "pages/auth/auth-forgot-password";
    }

}
