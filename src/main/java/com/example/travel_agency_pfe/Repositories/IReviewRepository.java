package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review,Long> {
    List<Review> getReviewsByAppUserUserName(String username);
    List<Review> getReviewsByEnabeledTrue();
    Long countByAppUserUserName(String username);
}
