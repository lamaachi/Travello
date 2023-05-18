package com.example.travel_agency_pfe.Services;


import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Review;

import java.util.List;
import java.util.Optional;

public interface IReviewService {
    List<Review> getAllReviews();
    List<Review> getAtiveReviews();
    List<Review> getReviewsByUser(String username);

    void deleteReview(Long id);
    Review save(Review travel);
    Optional<Review> getReviewById(Long id);


}


