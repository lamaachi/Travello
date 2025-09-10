package com.example.travel_agency_pfe.Configurations;


import com.example.travel_agency_pfe.Services.UserDetailsServiceImp;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private UserDetailsServiceImp userDetailsServiceImp;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
                .and().logout()
                .logoutSuccessUrl("/")
                .permitAll();
        httpSecurity.userDetailsService(userDetailsServiceImp);
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/","/subscribe","/pages/**","/fonts/**","/simple-datatables/**","/search/**","/Auth/**", "/webjars/**", "/css/**", "/error/**","/js/**","/videos/**","/images/**","/booknow/**","/travel-images/**").permitAll();
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/error/notAuthorized");
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        return httpSecurity.build();
    }
}
