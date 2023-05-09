package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Services.MailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class MailController {
    private MailService mailService;

    @GetMapping("/send-mail")
    public String showMailForm() {
        return "mail-form";
    }

    @PostMapping("/panel/admin/send-mail-to-client")
    public String sendMail(@RequestParam("toEmail") String toEmail,
                           @RequestParam("subject") String subject,
                           @RequestParam("message") String message, RedirectAttributes ra) throws MessagingException {

        String sub = "Email Tournest  Test subject";
        String msg = "Email Tournest  Test message";
        mailService.sendSimpleEmail(toEmail,subject,message);
        ra.addAttribute("message","Email has been Sent To client seccussfully!");
        return "redirect:/panel/admin/clients";
    }
}
