package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Configurations.FileUplaodUtil;
import com.example.travel_agency_pfe.Configurations.ImageUtil;
import com.example.travel_agency_pfe.Models.*;
import com.example.travel_agency_pfe.Repositories.IAgencyRepositry;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Repositories.ITravelRepository;
import com.example.travel_agency_pfe.Services.ITravelServiceImpl;
import com.example.travel_agency_pfe.Services.ImageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Controller
@AllArgsConstructor
public class TravelController {
    private IAppUserRepository appUserRepository;
    private ITravelServiceImpl travelService;
    private ITravelRepository travelRepository;

    private IAgencyRepositry iAgencyRepositry;


    @GetMapping("/panel/admin/travels")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String travelsList(Model model) {
        List<Travel> travels = travelRepository.findAll();
        model.addAttribute("travels", travels);
        return "pages/travels/travelsList";
    }

    @GetMapping("/panel/admin/travels/addnew")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newTravelForm(Model model){
        Travel travel = Travel.builder().build();
        model.addAttribute("travel", travel);
        return "pages/travels/addNewTravel";
    }

    @PostMapping("/panel/admin/travels/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addTravel(Authentication authentication, @ModelAttribute("travel") @Valid Travel tr, @RequestParam("file") MultipartFile image, BindingResult result, Model model, @RequestParam(value = "specialOffer", required = false) Boolean specialOffer) throws IOException {
        if (result.hasErrors()) {
            // Add error messages to the Thymeleaf model
            model.addAttribute("errors", result.getAllErrors());
            return "pages/travels/addNewTravel";
        }

        // Get the current user's username from the Authentication object
        String currentUsername = authentication.getName();
        // Find the AppUser object for the current user
        AppUser currentUser = appUserRepository.findByUserName(currentUsername);

        // Create a new Image object and save it
        Image newImage = Image.builder()
                .name(image.getOriginalFilename())
                .type(image.getContentType())
                .imageData(ImageUtil.compressImage(image.getBytes()))
                .build();
        // Create a new Travel object and set its properties
        boolean special = specialOffer != null && specialOffer;
        Travel travel = Travel.builder()
                .title(tr.getTitle())
                .travelDate(tr.getTravelDate())
                .travelType(tr.getTravelType())
                .exclus(tr.getExclus())
                .inclus(tr.getInclus())
                .specialOffer(special)
                .Activities(tr.getActivities())
                .image(newImage) // Set the image
                .price(tr.getPrice())
                .pricechuild(tr.getPricechuild())
                .destiantion(tr.getDestiantion())
                .nights(tr.getNights())
                .days(tr.getDays())
                .appUser(currentUser)
                .build();

        travelService.save(travel);

        // Redirect to the travel list page
        return "redirect:/panel/admin/travels?success";
    }


    @GetMapping("/panel/admin/travels/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteClient(Long id){
        travelService.deleteTravel(id);
        return "redirect:/panel/admin/travels?successdelete";
    }


        // details
        @GetMapping("/panel/admin/travels/{id}")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String detailsPage(@PathVariable("id") Long id, Model model) {
            Travel travel = travelService.getTravelById(id).orElse(null); // Use orElse(null) to handle the case where the travel is not found
            if (travel != null) {
                model.addAttribute("travel", travel);
                Image image = travel.getImage();
                if (image != null) {
                    byte[] imageData = ImageUtil.decompressImage(image.getImageData());
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
                    model.addAttribute("imageUrl", imageUrl);
                }
            }
            return "pages/travels/travelDetails";
        }

    @GetMapping("/search/travel")
    public String searchPage(@RequestParam(value = "destination", required = false) String destination,
                             @RequestParam(value = "traveldate", required = false) LocalDate travelDate,
                             @RequestParam(value = "days", required = false) Integer days,Model model  ){
        Subscriber subscriber = new Subscriber();
        model.addAttribute("subscriber",subscriber);
        List<Travel> travels = travelService.searchravels(destination, travelDate, days);
        Map<Long, String> travelImageMap = new HashMap<>();
        for (Travel travel : travels) {
            Image image = travel.getImage();
            byte[] imageData = ImageUtil.decompressImage(image.getImageData());
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            String imageUrl = "data:" + image.getType() + ";base64," + base64Image;
            travelImageMap.put(travel.getId(), imageUrl);
        }
        Agency agency = iAgencyRepositry.findById(1L).get();
        model.addAttribute("agency",agency);
        model.addAttribute("travels",travels);
        model.addAttribute("travelImageMap", travelImageMap);
        return  "pages/travels/searchTravel";
    }
    //Update travel
    @PostMapping("/panel/admin/travels/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateTravel(Authentication authentication, @ModelAttribute("travel") @Valid Travel updatedTravel,
                               @RequestParam(value = "file", required = false) MultipartFile image, BindingResult result, Model model,
                               @RequestParam(value = "specialOffer", required = false) Boolean specialOffer,
                               @RequestParam(value = "traveldate",required = false) LocalDate date ) throws IOException {
        if (result.hasErrors()) {
            // Add error messages to the Thymeleaf model
            model.addAttribute("errors", result.getAllErrors());
            return "pages/travels/travelDetails";
        }
        // Get the current user's username from the Authentication object
        String currentUsername = authentication.getName();
        // Find the AppUser object for the current user
        AppUser currentUser = appUserRepository.findByUserName(currentUsername);

        // Find the existing Travel object by ID
        Optional<Travel> optionalTravel = travelService.getTravelById(updatedTravel.getId());
        if (!optionalTravel.isPresent()) {
            // Handle the case when the travel is not found
            return "redirect:/panel/admin/travels?error=notfound";
        }
        Travel existingTravel = optionalTravel.get();
        // Update the properties of the existing Travel object
        existingTravel.setTitle(updatedTravel.getTitle());
        existingTravel.setTravelDate(updatedTravel.getTravelDate());
        existingTravel.setTravelType(updatedTravel.getTravelType());
        existingTravel.setExclus(updatedTravel.getExclus());
        existingTravel.setInclus(updatedTravel.getInclus());
        existingTravel.setActivities(updatedTravel.getActivities());
        existingTravel.setPrice(updatedTravel.getPrice());
        existingTravel.setPricechuild(updatedTravel.getPricechuild());
        existingTravel.setDestiantion(updatedTravel.getDestiantion());
        existingTravel.setNights(updatedTravel.getNights());
        existingTravel.setDays(updatedTravel.getDays());
        if(updatedTravel.getTravelDate()==null){
            existingTravel.setTravelDate(date);
        }
        else{
            existingTravel.setTravelDate(updatedTravel.getTravelDate());
        }

        existingTravel.setAppUser(currentUser);

        if (specialOffer == null) {
            existingTravel.setSpecialOffer(false);
        } else {
            existingTravel.setSpecialOffer(true);
        }

        if (!image.isEmpty()) {
            // Create a new Image object and save it
            Image newImage = Image.builder()
                    .name(image.getOriginalFilename())
                    .type(image.getContentType())
                    .imageData(ImageUtil.compressImage(image.getBytes()))
                    .build();

            // Update the existing Travel object's image
            existingTravel.setImage(newImage);
        }

        travelService.save(existingTravel);

        // Redirect to the travel list page
        return "redirect:/panel/admin/travels?successUpdate";
    }
}
