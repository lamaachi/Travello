package com.example.travel_agency_pfe;

import com.example.travel_agency_pfe.Services.AccountService;
import com.example.travel_agency_pfe.Services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TravelAgencyPfeApplication {


    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyPfeApplication.class, args);

    }

    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            accountService.addNewUser( "user1","x","x","user1@mailx.com","1234","2324524425","shadga");
            accountService.addNewUser( "user2","x","x","user2@mailx.com","1234","1234","2324524425");
            accountService.addNewUser( "admin","x","x","admin@mailx.com","admin","admin","2324524425");

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","ADMIN");

        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
