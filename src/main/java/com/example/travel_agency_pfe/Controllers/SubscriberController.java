package com.example.travel_agency_pfe.Controllers;


import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Repositories.ISubscriberRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class SubscriberController {
    private ISubscriberRepository subscriberRepository;

    @GetMapping("/panel/admin/subscribers")
    public String listSub(Model model){
        model.addAttribute("subList",subscriberRepository.findAll());
        return "pages/subscriber/subscribersList";
    }

    @PostMapping("/Auth/subscribe")
    public String subscribe(@Valid Subscriber subscriber ,BindingResult result, Model model) {

        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(subscriber.getEmail());
        if (optionalSubscriber.isEmpty()) {
            subscriberRepository.save(subscriber);
            return "redirect:/?successSub";
        }else{
            return "redirect:/?failSub";
        }
    }

    @GetMapping("/panel/admin/subscribers/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteClient(String id){
        subscriberRepository.deleteById(id);
        return "redirect:/panel/admin/subscribers?successdelete";
    }

}
