package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if(appUser==null) throw new UsernameNotFoundException(String.format("User %s Not found...",username));

        String[] roles = appUser.getRoles().stream().map(u-> u.getRole()).toArray(String[]::new);
        // boolean enabled = !appUser.isAccountVerified();
        UserDetails userDetails = User
                .withUsername(appUser.getUserName())
                //.disabled(enabled)
                .password(appUser.getPassword())
                .roles(roles).build();
        return userDetails;
    }
}
