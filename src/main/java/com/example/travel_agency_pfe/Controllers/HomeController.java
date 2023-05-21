package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Models.Review;
import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Repositories.ITravelRepository;
import com.example.travel_agency_pfe.Services.IReviewServiceImpl;
import com.example.travel_agency_pfe.Services.ITravelService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController{
    private ITravelService travelService;
    private IReviewServiceImpl reviewService;
    private ITravelRepository travelRepository;

    @GetMapping(value = {"/","/index","/pages/"})
    public String index(Model model, @RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value = "size",defaultValue = "3") int size){
        Page<Travel> travelList = travelRepository.findAll(PageRequest.of(page,size));
        List<Review> reviews = reviewService.getAtiveReviews();
        Subscriber subscriber = new Subscriber();
        Travel specialOffer =  travelRepository.findFirstBySpecialOfferTrueOrderByCreatedAtDesc();
        if(specialOffer!=null){
            model.addAttribute("specialOffer",specialOffer);
        }else{
            model.addAttribute("specialOffer",travelRepository.findFirstByOrderById());
        }
        model.addAttribute("travels",travelList.getContent());
        model.addAttribute("pages",new int[travelList.getTotalPages()]);
        model.addAttribute("curruntpage",page);
        model.addAttribute("reviews",reviews);
        model.addAttribute("subscriber",subscriber);
        return "index";
    }
    @GetMapping("/booknow/travel/{id}")
    public String booknow(@PathVariable("id") Long id,Model model){
        Subscriber subscriber = new Subscriber();
        model.addAttribute("subscriber",subscriber);
        model.addAttribute("travel",travelService.getTravelById(id).get());
        return "pages/travels/booknow";
    }
}




