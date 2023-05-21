package com.example.travel_agency_pfe;

import com.example.travel_agency_pfe.Services.AccountService;
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
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewRole("SUPER_ADMIN");
            accountService.addNewUser( "admin","x","x","admin@mailx.com","admin","admin","2324524425","jh23456");
            accountService.addRoleToUser("admin","ADMIN");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
