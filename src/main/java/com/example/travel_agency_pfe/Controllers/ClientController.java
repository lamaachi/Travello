package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Repositories.IAppRoleRepository;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Services.AccountServiceImpl;
import com.example.travel_agency_pfe.Services.IClientServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@AllArgsConstructor @NoArgsConstructor
public class ClientController {
    @Autowired
    private IClientServiceImpl iClientService;
    @Autowired
    private IAppUserRepository appUserRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private IAppRoleRepository appRoleRepository;

    @GetMapping("/panel/admin/clients/delete")
    public String deleteClient(String id,Model model){
        AppUser user = appUserRepository.findById(id).get();
        if(user.getUserName().equals("admin")){
            model.addAttribute("adminFailed","Could Not delete This Account!");
            return "redirect:/panel/admin/clients";
        }
        iClientService.deleteClient(id);
        return "redirect:/panel/admin/clients";
    }

    @GetMapping("/panel/admin/clients/{id}")
    public String updateForn(@PathVariable("id") String id, Model model, Principal principal){
        String currentuser = principal.getName();
        System.out.println("==============================================:"+currentuser);
        model.addAttribute("user",iClientService.getById(id));
        model.addAttribute("currentname",currentuser);
        return "pages/clients/editClient";
    }

    @PostMapping("/panel/admin/clients/update")
    public String updateUser(AppUser user, @RequestParam(value = "isadmin" ,required = false) Boolean isadmin, RedirectAttributes ra){
        Optional<AppRole> appRoleUser = appRoleRepository.findById("USER");
        Optional<AppRole> appRoleAdmin = appRoleRepository.findById("ADMIN");
        if(isadmin!=null && isadmin ){
            user.getRoles().remove(appRoleUser.get());
            user.getRoles().add(appRoleAdmin.get());
            user.setIsadmin(true);
        }else{
            user.getRoles().remove(appRoleAdmin.get());
            user.getRoles().add(appRoleUser.get());
            user.setIsadmin(false);
        }
        iClientService.save(user);
        ra.addAttribute("updateMessage","The user has been updated successfully.");
        return "redirect:/panel/admin/clients";
    }


}
