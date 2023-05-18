package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Review;
import com.example.travel_agency_pfe.Repositories.IReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class IReviewServiceImpl implements IReviewService {
    private IReviewRepository reviewRepository;
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getAtiveReviews() {
        return reviewRepository.getReviewsByEnabeledTrue();
    }

    @Override
    public List<Review> getReviewsByUser(String username) {
        return reviewRepository.getReviewsByAppUserUserName(username);
    }


    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Review save(Review travel) {
        return reviewRepository.save(travel);
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
}
