package com.example.travel_agency_pfe;

import com.example.travel_agency_pfe.Services.AccountService;
import com.example.travel_agency_pfe.Services.AccountServiceImpl;
import com.example.travel_agency_pfe.Services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
//@ComponentScan("com.example.travel_agency_pfe.Models")
public class TravelAgencyPfeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyPfeApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountServiceImpl accountService){
        return args -> {
            if (!accountService.roleExists("USER")) {
                accountService.addNewRole("USER");
            }
            if (!accountService.roleExists("ADMIN")) {
                accountService.addNewRole("ADMIN");
            }
            if (!accountService.roleExists("SUPER_ADMIN")) {
                accountService.addNewRole("SUPER_ADMIN");
            }

            if (!accountService.userExists("admin")) {
                accountService.addNewUser( "admin","adminFirstName","adminLastName","admin@mail.com","admin","00000000000","adminAddress","CIN",true);
            }

            if (!accountService.hasRole("admin", "ADMIN")) {
                accountService.addRoleToUser("admin", "ADMIN");
            }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
