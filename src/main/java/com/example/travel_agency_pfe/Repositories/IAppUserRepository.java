package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUserName(String username);
    AppUser getAppUserByUserId(String id);
    List<AppUser> getAllByResetPasswordToken(String token);
    @Query("SELECT a FROM AppUser a WHERE a.email = ?1")
    AppUser findByEmail(String email);

    AppUser findByResetPasswordToken(String token);

    Boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM AppUser u WHERE u.userName = :username OR u.email = :email")
    boolean existsByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
    boolean findAppUserByUserNameOrEmail(String username,String email);
    boolean existsByUserName(String name);
//    @Query("SELECT u FROM AppUser u WHERE u.verificationCode = ?1")
//    public AppUser findByVerificationCode(String code);
}
