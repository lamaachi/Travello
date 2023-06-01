package com.example.travel_agency_pfe.Controllers.Auth;

import com.example.travel_agency_pfe.Models.*;
import com.example.travel_agency_pfe.Repositories.*;
import com.example.travel_agency_pfe.Services.AccountServiceImpl;
import com.example.travel_agency_pfe.Services.IClientServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor @NoArgsConstructor
public class AuthController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private IAppUserRepository iAppUserRepository;
    @Autowired
    private IClientRepository iClientRepository;
    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;
    @Autowired
    private ITravelRepository travelRepository;
    @Autowired
    private IResevationRepository resevationRepository;
    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private IAgencyRepositry agencyRepositry;

    @GetMapping("/login")
    public String loginPage(Model model){
        Subscriber subscriber = new Subscriber();
        model.addAttribute("subscriber",subscriber);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "pages/auth/auth-login";
        }

        return "redirect:/";
    }

    @GetMapping("/panel")
    public String adminPanel(Model model,Principal principal){
        String currentUser = principal.getName(); // Get the username of the current user
        long countres = resevationRepository.countByAppUserUserName(currentUser);
        long countreviews = reviewRepository.countByAppUserUserName(currentUser);
        if (isAdmin(currentUser)) {
            // If the current user is an admin, return all reviews
            countres = resevationRepository.count();
            countreviews = reviewRepository.count();
        }
        model.addAttribute("counttravels",travelRepository.count());
        model.addAttribute("countres",countres);
        model.addAttribute("countclients",iClientRepository.count());
        model.addAttribute("countreviews",countreviews);
        return "landing_page";
    }

    private boolean isAdmin(String currentUser) {
        AppUser user = iAppUserRepository.findByUserName(currentUser);

        if (user != null) {
            for (AppRole role : user.getRoles()) {
                if (role.getRole().equals("ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/Auth/register")
    public String registerPage(Model model){
        model.addAttribute("appUser", new AppUser());
        return "pages/auth/auth-register";
    }
    @PostMapping("/Auth/register")
    public String processRegistrationForm(@ModelAttribute("appUser") @Valid AppUser appUser,
                                          BindingResult result, Model model,RedirectAttributes ra,HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        // Check if there is an existing user with the same username or email
        boolean existingUser = iAppUserRepository.existsByUsernameOrEmail(appUser.getUserName(), appUser.getEmail());
        if (existingUser) {
            // Add error message to the Thymeleaf model
            model.addAttribute("errorMessage", "Username or email already exists. Please choose a different one.");
            return "pages/auth/auth-register";
        }

        if (result.hasErrors()) {
            // Add error messages to the Thymeleaf model
            model.addAttribute("errors", result.getAllErrors());
            return "pages/auth/auth-register";
        }
        ra.addFlashAttribute("message", "You have signed up successfully! Please check your email to verify your account.");
        accountService.addNewUser(
                appUser.getUserName(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getPassword(),
                appUser.getPhone(),
                appUser.getAdress(),
                appUser.getCIN(),
                appUser.getIsadmin()
        );
        accountService.register(appUser, getSiteURL(request));
        return "redirect:/login";
    }
    @GetMapping("/Auth/getpassword")
    public String forgotPassPage(){
        return "pages/auth/auth-forgot-password";
    }
    @GetMapping("/Auth/profile")
    public String profile(Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AppUser currentUser = iClientRepository.getAppUserByUserName(userDetails.getUsername());
        System.out.println("=============================userid:"+currentUser.getUserId());
        Agency agency = agencyRepositry.findFirstByOrderByName();
        model.addAttribute("user", currentUser);
        model.addAttribute("agency", agency);
        return "pages/auth/auth-profile";
    }
    @PostMapping("/Auth/profile/update")
    public String updateProfile(@Valid @ModelAttribute("user") AppUser appUser, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return the form with error messages
            return "pages/auth/auth-profile";
        }
        // Retrieve the existing user from the database using the user ID
        AppUser existingUser = iAppUserRepository.findByUserName(appUser.getUserName());
        if (existingUser != null) {
            // Update the necessary fields
            //existingUser.setUserId(appUser.getUserId());
            existingUser.setUserName(appUser.getUserName());
            existingUser.setCIN(appUser.getCIN());
            existingUser.setFirstName(appUser.getFirstName());
            existingUser.setLastName(appUser.getLastName());
            existingUser.setEmail(appUser.getEmail());
            existingUser.setAdress(appUser.getAdress());
            existingUser.setPhone(appUser.getPhone());
            // Keep the old password
            existingUser.setPassword(existingUser.getPassword());
            // Save the updated user to the database
            iAppUserRepository.save(existingUser);
        }
        model.addAttribute("successUpdateProfile","Your Data Has been updated succussfully.");
        return "redirect:/Auth/profile";
    }


    @PostMapping("/Auth/profile/changePassword")
    public String changePassword(@RequestParam("newpassword") String newpassword,@RequestParam("oldpassword") String oldpassword, Model model){
        System.out.println("============================old"+oldpassword);
        System.out.println("============================new"+newpassword);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AppUser currentUser = iClientRepository.getAppUserByUserName(userDetails.getUsername());
        if(this.cryptPasswordEncoder.matches(oldpassword,currentUser.getPassword())){
            currentUser.setPassword(this.cryptPasswordEncoder.encode(newpassword));
            iClientRepository.save(currentUser);
            System.out.println("==============="+this.cryptPasswordEncoder.encode(newpassword));
            System.out.println("===============Password canged");
            model.addAttribute("successUpdatePass","Your Password  updated succussfully.");
            model.addAttribute("user", currentUser);
            return  "redirect:/Auth/profile";
        }else{
            model.addAttribute("failold","The old Password not match!");
            model.addAttribute("user", currentUser);
            System.out.println("===============Password not canged");
            return  "redirect:/Auth/profile";
        }
    }

    @PostMapping("/Auth/getpassword")
    public String processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 30);

        boolean tokenUpdateSuccessful = accountService.updateResetPasswordToken(token, email);

        if (tokenUpdateSuccessful) {
            String resetPasswordLink = Utility.getSiteURL(request) + "/Auth/reset_password?token=" + token;

            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } else {
            model.addAttribute("errorMessage", "Could not find any customer with the email " + email);
        }

        return "pages/auth/auth-forgot-password";
    }



    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "TourNest Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/Auth/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        AppUser user = accountService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "pages/auth/auth-reset-password";
    }

    @PostMapping("/Auth/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        List<AppUser> customers = iAppUserRepository.getAllByResetPasswordToken(token);

        if (customers.isEmpty()) {
            model.addAttribute("errorMessage", "Invalid Token");
            return "pages/auth/auth-reset-password";
        } else if (customers.size() > 1) {
            model.addAttribute("errorMessage", "Multiple users found with the same reset password token. Please contact support.");
            return "pages/auth/auth-reset-password";
        } else {
            AppUser customer = customers.get(0);
            accountService.updatePassword(customer, password);
            model.addAttribute("errorMessage", "You have successfully changed your password.");
        }
        return "redirect:/login";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}


