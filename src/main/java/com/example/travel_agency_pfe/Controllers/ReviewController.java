package com.example.travel_agency_pfe.Controllers;


import com.example.travel_agency_pfe.Models.AppRole;
import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Review;
import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Services.AccountServiceImpl;
import com.example.travel_agency_pfe.Services.IReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReviewController {
    private AccountServiceImpl accountService;
    private IReviewService reviewService;
    private IAppUserRepository appUserRepository;
    @GetMapping("/panel/reviews")
    public String travelsList(Model model, Principal principal){
        String currentUser = principal.getName(); // Get the username of the current user

        List<Review> reviews;

        if (isAdmin(currentUser)) {
            // If the current user is an admin, return all reviews
            reviews = reviewService.getAllReviews();
        } else {
            // If the current user is not an admin, return reviews added by the current user
            reviews = reviewService.getReviewsByUser(currentUser);
        }
        model.addAttribute("reviews", reviews);
        return "pages/reviews/ReviewsList";
    }

    private boolean isAdmin(String currentUser) {
        AppUser user = appUserRepository.findByUserName(currentUser);

        if (user != null) {
            for (AppRole role : user.getRoles()) {
                if (role.getRole().equals("ADMIN")) {
                    return true;
                }
            }
        }

        return false;
    }

    @GetMapping("/panel/reviews/addnew")
    public String newTravelForm(Model model){
        Travel travel = Travel.builder().build();
        model.addAttribute("travel", travel);
        return "pages/reviews/addNewReview";
    }

    @PostMapping("/panel/Reviews/save")
    public String addReview(@RequestParam("title") String title, @RequestParam("content") String content, Authentication authentication) {
        Review review = new Review();
        review.setTitle(title);
        review.setRev_content(content);
        review.setEnabeled(false);

        String loggedInUsername = authentication.getName();
        AppUser loggedInUser = appUserRepository.findByUserName(loggedInUsername);

        review.setAppUser(loggedInUser);

        reviewService.save(review);
        return "redirect:/panel/reviews";
    }

    @GetMapping("/panel/reviews/delete")
    public String deleteClient(Long id){
        reviewService.deleteReview(id);
        return "redirect:/panel/reviews?successdelete";
    }

    @GetMapping("/panel/reviews/{id}")
    public String detailsPage(@PathVariable("id") Long id, Model model){
        model.addAttribute("review",reviewService.getReviewById(id).get());
        return "pages/reviews/reviewdetails";
    }

    @PostMapping("/panel/Reviews/update")
    public String updateReview(@ModelAttribute("review") Review review, @RequestParam(value = "enabeled",required = false) boolean enabled) {
        // Fetch the existing review from the database
        Review existingReview = reviewService.getReviewById(review.getId()).get();
        // Set the updated values
        existingReview.setTitle(review.getTitle());
        existingReview.setRev_content(review.getRev_content());
        existingReview.setEnabeled(enabled);
        // Save the updated review to the database
        reviewService.save(existingReview);
        // Redirect to the reviews list page or any other appropriate page
        return "redirect:/panel/reviews?successUpdate";
    }

}
