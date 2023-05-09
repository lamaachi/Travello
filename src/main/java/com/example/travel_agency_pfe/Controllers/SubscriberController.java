package com.example.travel_agency_pfe.Controllers;


import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Repositories.ISubscriberRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class SubscriberController {
    private ISubscriberRepository subscriberRepository;
    @PostMapping("/subscribe")
    public String subscribe(@Valid Subscriber subscriber ,BindingResult result, Model model) {
        try {
            subscriberRepository.save(subscriber);
            return "redirect:/?successSub";
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("unique_email_constraint")) {
                result.rejectValue("email", "error.subscriber",
                        "This email is already registered. Please use a different email.");
                return "index";
            }
            throw ex;
        }

    }
}
