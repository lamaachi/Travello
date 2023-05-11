package com.example.travel_agency_pfe.Models;

import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Services.AccountServiceImpl;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity @NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "users")
public class AppUser {

    @Id
    private String userId;
    @NotBlank(message = "User name is required")
    private String userName;
    @NotBlank(message = "FirstName  is required")
    private String firstName;
    @NotBlank(message = "LastName is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password is required")
    @Length(min = 6,message = "Password should be min 6 char")
    private String password;

    @NotBlank(message = "Adress is required")
    private String adress;
    @NotBlank(message = "Phone Number is required")
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<AppRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Travel> travels;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Override
    public String toString() {
        return "Travel";
    }
}
