package com.example.travel_agency_pfe.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
public class  AppUser {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "CIN is required")
    private String CIN;

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

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @NotBlank(message = "Adress is required")
    private String adress;
    @NotBlank(message = "Phone Number is required")
    private String phone;

    private Boolean isadmin;


    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<AppRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Travel> travels;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Reservation> reservations;


    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Override
    public String toString() {
        return "AppUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", CIN='" + CIN + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", resetPasswordToken='" + resetPasswordToken + '\'' +
                ", adress='" + adress + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
