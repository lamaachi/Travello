package com.example.travel_agency_pfe.Configurations;


import com.example.travel_agency_pfe.Services.UserDetailsServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private PasswordEncoder passwordEncoder;
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
                .requestMatchers("/","/fonts/**","/simple-datatables/**","/search/**","/subscribe","/Auth/**", "/webjars/**", "/css/**", "/error/**","/js/**","/videos/**","/images/**","/booknow/**","/travel-images/**").permitAll();
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/error/notAuthorized");
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        return httpSecurity.build();
    }
}
