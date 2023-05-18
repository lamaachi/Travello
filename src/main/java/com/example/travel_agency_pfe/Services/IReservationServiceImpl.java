package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Reservation;
import com.example.travel_agency_pfe.Models.Review;
import com.example.travel_agency_pfe.Repositories.IResevationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.JavaBean;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IReservationServiceImpl implements IReservationService {
    private IResevationRepository resevationRepository;
    private JavaMailSender mailSender;
    @Override
    public Reservation save(Reservation reservation) {
        return resevationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return resevationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByUser(String username) {
        return resevationRepository.getReservationsByAppUserUserName(username);
    }

    @Override
    public void deleteRes(Long id) {
        resevationRepository.deleteById(id);
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return resevationRepository.findById(id);
    }

    public void sendReservationEmail(AppUser user, String siteURL, Reservation reservation)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "lamaachi.officiel@gmail.com";
        String senderName = "TourNest Travel Agency";
        String subject = "Reservation Confirmation";
        // Generate the content of the email
        String content = "<html>" +
                "<body>" +
                "<div style=\"font-family: Arial, sans-serif;\">" +
                "<h2 style=\"color: #007bff;\">Reservation Confirmation</h2>" +
                "<p>Dear " + user.getUserName() + ",</p>" +
                "<p>Thank you for choosing TourNest Travel Agency! We have received your reservation and it is pending payment for validation. Please find below the details of your reservation:</p>" +
                "<table style=\"width: 100%;\">" +
                "<tr><th>Reservation ID:</th><td>" + reservation.getId() + "</td></tr>" +
                "<tr><th>Number of Adults:</th><td>" + reservation.getNumberOfAdults() + "</td></tr>" +
                "<tr><th>Number of Children:</th><td>" + reservation.getNumberOfChildren() + "</td></tr>" +
                "<tr><th>Total Amount:</th><td>" + reservation.getTotalAmount() + " MAD</td></tr>" +
                "</table>" +
                "<p>To proceed with the payment and validate your reservation, you can make the payment through bank transfer or visit our agency to pay in cash.</p>" +
                "<strong>Bank Transfer Details:</strong><br>" +
                "<ul>" +
                "<li>Bank Name: Fake Bank</li>" +
                "<li>Account Number: XXXXXXXX</li>" +
                "<li>IBAN: XXXXXXXXXXXX</li>" +
                "<li>SWIFT/BIC: XXXXXXXX</li>" +
                "</ul>" +
                "<strong>Cash Payment at Agency:</strong><br>" +
                "<p>Agency Address: 123 Main Street, City, Country</p>" +
                "<p>Please note that your reservation will be automatically canceled if the payment is not made within 48 hours.</p>" +
                "<p>If you have any questions or need assistance, please feel free to contact us.</p>" +
                "<p>Thank you for choosing TourNest Travel Agency. We look forward to serving you and providing you with a memorable travel experience!</p>" +
                "<p>TourNest Travel Agency</p>" +
                "</div>" +
                "</body>" +
                "</html>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }


    public String generateTicketContent(Reservation reservation) {
        // Generate the ticket HTML content based on the reservation details
        return "Dear " + reservation.getAppUser().getUserName() + ",<br><br>"
                + "Thank you for your reservation. Please find below your ticket details:<br><br>"
                + "Reservation ID: " + reservation.getId() + "<br>"
                + "Number of Adults: " + reservation.getNumberOfAdults() + "<br>"
                + "Number of Children: " + reservation.getNumberOfChildren() + "<br>"
                + "Total Amount: " + reservation.getTotalAmount() + " MAD" + "<br><br>"
                + "We look forward to providing you with a memorable travel experience.<br><br>"
                + "TourNest Travel Agency";
    }

    public void sendTicketEmail(String toAddress, String content)
            throws MessagingException, UnsupportedEncodingException {
        String fromAddress = "lamaachi.officiel@gmail.com";
        String senderName = "TourNest Travel Agency";
        String subject = "Your Reservation Ticket";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
        System.out.println("Ticket email sent successfully.");
    }



}
