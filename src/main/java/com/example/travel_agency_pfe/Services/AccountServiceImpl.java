package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Repositories.IAppRoleRepository;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private IAppRoleRepository iAppRoleRepository;
    @Autowired
    private IAppUserRepository iAppUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //add new user method
    @Override
    public void addNewUser(String username, String firstn, String lastn, String email, String password, String phone, String adress,String cin,Boolean isadmin) {
        Optional<AppRole> appRole = iAppRoleRepository.findById("USER");
        AppUser appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .userName(username)
                .firstName(firstn)
                .lastName(lastn)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .adress(adress)
                .CIN(cin)
                .isadmin(false)
                .build();
        appUser.getRoles().add(appRole.get());
        iAppUserRepository.save(appUser);
    }

    //add new Role method
    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = iAppRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("this Role alredy exist!!!");
        //creat New App User
        appRole = AppRole.builder()
                .role(role)
                .build();
        return iAppRoleRepository.save(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return iAppUserRepository.findByUserName(username);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = iAppUserRepository.findByUserName(username);
        Optional<AppRole> appRole = iAppRoleRepository.findById(role);
        AppRole savedRole = appRole.orElseThrow(() -> new RuntimeException("Role not found"));
        appUser.getRoles().add(savedRole);
        System.out.println("=============================="+appUser+"================"+savedRole);
    }
    @Override
    public AppRole getAppRoleByRole(String role) {
        return iAppRoleRepository.getAppRoleByRole(role);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = iAppUserRepository.findByUserName(username);
        Optional<AppRole> appRole = iAppRoleRepository.findById(role);
        AppRole savedRole = appRole.orElseThrow(() -> new RuntimeException("Role not found"));
        appUser.getRoles().remove(savedRole);
    }

    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        return iAppUserRepository.findAppUserByUserNameOrEmail(username,email);
    }

    public boolean updateResetPasswordToken(String token, String email){
        AppUser user = iAppUserRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            iAppUserRepository.save(user);
            return true; // Token update successful
        } else {
            return false; // User not found
        }
    }

    public AppUser getByResetPasswordToken(String token) {
        return iAppUserRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(AppUser user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        iAppUserRepository.save(user);
    }


    public void register(AppUser user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {

        System.out.println("==================================================user:"+user);
        System.out.println("==================================================url:"+siteURL);
        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(AppUser user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "lamaachi.officiel@gmail.com";
        String senderName = "TourNest Travel Agency ";
        String subject = "Please verify your Informations";
        String content = "Dear [[name]],<br>"
                + "Welcome to TourNest Travel Agency! Please click the link below to log in:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Log In</a></h3>"
                + "Thank you for joining us. We look forward to providing you with the best travel experiences.<br>"
                + "If you have any questions or need assistance, please feel free to contact us.<br>"
                + "Enjoy your journey!<br>"
                + "TourNest Travel Agency";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUserName());
        String verifyURL = siteURL + "/login";

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        System.out.println("===========================send email=======================");
        System.out.println("==================================================content:"+content);


    }




    public boolean roleExists(String roleName) {
        return iAppRoleRepository.existsByRole(roleName);
    }

    public boolean userExists(String username) {
        return iAppUserRepository.existsByUserName(username);
    }

    public boolean hasRole(String username, String roleName) {
        AppUser user = iAppUserRepository.findByUserName(username);
        if (user == null) {
            return false;
        }
        for (AppRole role : user.getRoles()) {
            if (role.getRole().equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }
}


