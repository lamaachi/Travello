package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Repositories.ISubscriberRepository;
import com.example.travel_agency_pfe.Services.MailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Controller
@AllArgsConstructor
public class MailController {
    private MailService mailService;
    @Autowired
    private ISubscriberRepository subscriberRepository;

    @GetMapping("/send-mail")
    public String showMailForm() {
        return "mail-form";
    }

    @PostMapping("/panel/admin/send-mail-to-client")
    public String sendMail(@RequestParam("toEmail") String toEmail,
                           @RequestParam("subject") String subject,
                           @RequestParam("message") String message, RedirectAttributes ra) throws MessagingException {

        mailService.sendSimpleEmail(toEmail,subject,message);
        ra.addAttribute("message","Email has been Sent To client seccussfully!");
        return "redirect:/panel/admin/clients";
    }


    @PostMapping("/panel/admin/subscribers/send-mail-to-subscribers")
    public String sendMailSubscribers(
            @RequestParam("subject") String subject,
            @RequestParam("message") String message, Model model) throws MessagingException {
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Create a thread pool with 10 threads
        List<Future<Void>> futures = new ArrayList<>();

        for (Subscriber subscriber : subscriberList) {
            String toEmail = subscriber.getEmail();
            Callable<Void> emailTask = createEmailTask(toEmail, subject, message);

            Future<Void> future = executorService.submit(emailTask);
            futures.add(future);
        }

        // Wait for all email tasks to complete
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                // Handle any exceptions that occurred during email sending
                e.printStackTrace();
            }
        }

        executorService.shutdown(); // Shut down the executor service

        model.addAttribute("message", "Emails have been sent to subscribers successfully!");
        return "redirect:/panel/admin/subscribers?messageSend";
    }

    private Callable<Void> createEmailTask(String toEmail, String subject, String message) {
        return () -> {
            mailService.sendSimpleEmail(toEmail, subject, message);
            return null;
        };
    }

}
