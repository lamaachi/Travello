package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Export.ReservationPDFExport;
import com.example.travel_agency_pfe.Models.*;
import com.example.travel_agency_pfe.Repositories.IAgencyRepositry;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Repositories.IInvoiceRepository;
import com.example.travel_agency_pfe.Services.IInvoiceServiceImpl;
import com.example.travel_agency_pfe.Services.IReservationServiceImpl;
import com.example.travel_agency_pfe.Services.ITravelServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReservationController {
    private ITravelServiceImpl travelService;
    private IAppUserRepository userRepository;
    private IInvoiceRepository invoiceRepository;
    private IReservationServiceImpl reservationService;
    private IInvoiceServiceImpl invoiceService;
    private IAgencyRepositry iAgencyRepositry;

    @GetMapping("/reserve")
    public String passeReservation(@RequestParam("childs") int childs, @RequestParam("adults") int adults ,@RequestParam("id") Long id, Model model){
            Travel travel = travelService.getTravelById(id).get();
            Subscriber subscriber = new Subscriber();
            Reservation reservation = new Reservation();
            reservation.setNumberOfChildren(childs);
            reservation.setNumberOfAdults(adults);
            double amount = travel.getPrice()*adults+travel.getPricechuild()*childs;
            model.addAttribute("travel",travel);
            model.addAttribute("amount",amount);
            model.addAttribute("reservation",reservation);
            Agency agency = iAgencyRepositry.findById(1L).get();
            model.addAttribute("agency",agency);
            model.addAttribute("subscriber",subscriber);
            return "pages/reservations/reservation-suc";
    }
    @PostMapping("/validateOrder")
    public String createReservation(Model model,Authentication authentication,@ModelAttribute("reservation") Reservation reservation,
                                    @RequestParam("travelId") Long travelId,
                                    @RequestParam("childs") int childs,
                                    HttpServletRequest request,
                                    @RequestParam("adults") int adults) throws MessagingException, UnsupportedEncodingException {
        // Retrieve the travel and user from the database
        Travel travel = travelService.getTravelById(travelId).get();
        double amount = travel.getPrice()*adults+travel.getPricechuild()*childs;
        // Get the current user's username from the Authentication object
        String currentUsername = authentication.getName();
        // Find the AppUser object for the current user
        AppUser currentUser = userRepository.findByUserName(currentUsername);
        // Create a new reservation
        reservation.setNumberOfAdults(adults);
        reservation.setNumberOfChildren(childs);
        reservation.setTotalAmount(amount);
        reservation.setTravel(travel);
        reservation.setAppUser(currentUser);
        // Save the reservation
        reservationService.save(reservation);
        Subscriber subscriber = new Subscriber();
        model.addAttribute("subscriber",subscriber);
        model.addAttribute("user",currentUser);
        Agency agency = iAgencyRepositry.findById(1L).get();
        model.addAttribute("agency",agency);
        //send email
        reservationService.sendReservationEmail(currentUser,getSiteURL(request),reservation);
        //Redirect to a success page or return a success message
        return "pages/reservations/reservation-passed";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/panel/reservations")
    public String travelsList(Model model, Principal principal){
        String currentUser = principal.getName(); // Get the username of the current user

        List<Reservation> reservations;

        if (isAdmin(currentUser)) {
            // If the current user is an admin, return all reviews
            reservations = reservationService.getAllReservations();
        } else {
            // If the current user is not an admin, return reviews added by the current user
            reservations = reservationService.getReservationsByUser(currentUser);
        }
        model.addAttribute("reservations", reservations);

        return "pages/reservations/reservationsList";
    }

    @GetMapping("/panel/reservations/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Reservations_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Reservation> listUsers = reservationService.getAllReservations();

        ReservationPDFExport exporter = new ReservationPDFExport(listUsers);
        exporter.export(response);
    }
    private boolean isAdmin(String currentUser) {
        AppUser user = userRepository.findByUserName(currentUser);

        if (user != null) {
            for (AppRole role : user.getRoles()) {
                if (role.getRole().equals("ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/panel/reservations/delete")
    public String deleteClient(Long id){
        reservationService.deleteRes(id);
        return "redirect:/panel/reservations?successdelete";
    }
    //update
    @GetMapping("/panel/reservations/{id}")
    public String detailsPage(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice",invoiceService.findAllByReservation(reservationService.getReservationById(id).get()));
        model.addAttribute("reservation",reservationService.getReservationById(id).get());
        return "pages/reservations/res-details";
    }

    @PostMapping("/panel/reservations/update")
    public String updateReview(Model model,@ModelAttribute("review") Reservation reservation,@RequestParam("travelId") Long travelId,@RequestParam(value = "invoiceid", required = false) Long invoiceid, @RequestParam(value = "payed",required = false) boolean payed,Principal principal) {
        String currentUser = principal.getName(); // Get the username of the current user
        // Fetch the existing review from the database
        Reservation existingRes = reservationService.getReservationById(reservation.getId()).get();
        Travel travel = travelService.getTravelById(travelId).get();
        double amount = travel.getPrice()*reservation.getNumberOfAdults()+travel.getPricechuild()*reservation.getNumberOfChildren();
        // Set the updated values of invoice
        if (invoiceid != null) {
            Invoice invoice = invoiceRepository.findById(invoiceid).get();
            invoice.setAmount(amount);
            invoiceRepository.save(invoice);
        }
        //existingRes.setTravel(reservation.getTravel());
        if(payed==false && invoiceid!=null){
            Invoice invoice = invoiceRepository.findById(invoiceid).get();
            if(invoice!=null){
                invoiceRepository.deleteById(invoiceid);
                Reservation res = reservationService.getReservationById(reservation.getId()).get();
                res.setInvoiced(false);
            }
        }else{

        }
        if (isAdmin(currentUser)) {
            // If the current user is an admin, return all reviews
            existingRes.setPayed(payed);
        }
        existingRes.setTotalAmount(amount);
        existingRes.setNumberOfChildren(reservation.getNumberOfChildren());
        existingRes.setNumberOfAdults(reservation.getNumberOfAdults());
        // Save the updated review to the database
        reservationService.save(existingRes);
        // Redirect to the reviews list page or any other appropriate page
        model.addAttribute("messageupdate","The reservation Updated SuccessFully...");
        return "redirect:/panel/reservations/"+reservation.getId();
    }

    @PostMapping("/panel/reservations/invoice")
    public String invoiceReservation(@ModelAttribute("reservation") Reservation res,
                                     @RequestParam("id") Long id,
                                     @RequestParam("user") String user,
                                     @RequestParam("travel") Long travelid,
                                     Model model) {
        Reservation reservation = reservationService.getReservationById(id).get();
        // Create a new invoice
        Invoice invoice = new Invoice();
        invoice.setAmount(reservation.getTotalAmount());
        invoice.setDate(LocalDateTime.now());
        invoice.setReservation(reservation);
        reservation.setInvoiced(true);
        // Save the invoice to the database
        invoiceService.createNewInvoice(invoice);
        System.out.println("=======================================================invoice id:"+invoice.getId());
        AppUser appUser = userRepository.findByUserName(user);
        Travel travel = travelService.getTravelById(travelid).get();
        reservation.setTravel(travel);
        reservation.setAppUser(appUser);
        reservationService.save(reservation);
        System.out.println("=======================================================invoiced orb not :"+reservation.getInvoiced());
        // Generate the ticket HTML
        String ticketContent = reservationService.generateTicketContent(reservation,invoice);
        // Send the ticket email to the client
        try {
            reservationService.sendTicketEmail(reservation.getAppUser().getEmail(), ticketContent);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle email sending failure
        }

        model.addAttribute("messageinvoice", "Congrats! This reservation has just been invoiced. An email with invoice information and ticket has been sent to the client.");
        return "redirect:/panel/reservations/" + reservation.getId();
    }


    @PostMapping("/panel/reservations/resend")
    public String ressendTicket(@ModelAttribute("reservation") Reservation res,@RequestParam("id") Long id,@RequestParam("user") String user,@RequestParam("travel") Long travelid){
        Reservation reservation = reservationService.getReservationById(id).get();
        Invoice invoice =reservation.getInvoice();
        // Generate the ticket HTML content
        String ticketContent = reservationService.generateTicketContent(reservation,invoice);
        // Send the ticket email to the client
        try {
            reservationService.sendTicketEmail(reservation.getAppUser().getEmail(), ticketContent);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle email sending failure
        }
        return  "redirect:/panel/reservations/" + reservation.getId();
    }


    @GetMapping("/panel/reservations/invoice/delete")
    @Transactional
    public String deleteinvoice(@RequestParam("id") Long id,@RequestParam("resid") Long resid){
        invoiceRepository.deleteById(id);
        Reservation reservation = reservationService.getReservationById(resid).get();
        reservation.setInvoiced(null);
        reservationService.save(reservation);
        return "redirect:/panel/reservations?successdelete";
    }


}

