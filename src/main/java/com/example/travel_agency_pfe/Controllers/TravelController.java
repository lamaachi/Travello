package com.example.travel_agency_pfe.Controllers;

import com.example.travel_agency_pfe.Configurations.FileUplaodUtil;
import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Repositories.ITravelRepository;
import com.example.travel_agency_pfe.Services.ITravelServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@AllArgsConstructor
public class TravelController {
    private IAppUserRepository appUserRepository;
    private ITravelServiceImpl travelService;


    @GetMapping("/panel/admin/travels")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String travelsList(Model model){
        List<Travel> travels = travelService.getAllTravels();

        model.addAttribute("travels",travels);

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
    public String addTravel(Authentication authentication, @ModelAttribute("travel") @Valid Travel tr , @RequestParam("file") MultipartFile image, BindingResult result, Model model,@RequestParam(value="specialOffer", required=false) Boolean specialOffer) throws IOException {
        if (result.hasErrors()) {
            // Add error messages to the Thymeleaf model
            model.addAttribute("errors", result.getAllErrors());
            return "pages/travels/addNewTravel";
        }
        // Get the current user's username from the Authentication object
        String currentUsername = authentication.getName();
        // Find the AppUser object for the current user
        AppUser currentUser = appUserRepository.findByUserName(currentUsername);
        // Create a new Travel object and set its properties
        boolean special = true;
        if(specialOffer==null){
            special=false;
        }
        if(!image.isEmpty()){
            String originalFilename  = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String filename = System.currentTimeMillis()+ "_" + originalFilename;
            Travel travel = Travel.builder()
                    .id(tr.getId())
                    .title(tr.getTitle())
                    .travelDate(tr.getTravelDate())
                    .travelType(tr.getTravelType())
                    .exclus(tr.getExclus())
                    .inclus(tr.getInclus())
                    .specialOffer(special)
                    .Activities(tr.getActivities())
                    .image(filename)
                    .price(tr.getPrice())
                    .destiantion(tr.getDestiantion())
                    .nights(tr.getNights())
                    .days(tr.getDays())
                    .appUser(currentUser)
                    .build();
                    travelService.save(travel);

            String upload = "src/main/resources/static/travel-images";
            FileUplaodUtil.saveFile(upload,filename,image);
        }else{
            if(tr.getImage().isEmpty()){
                Travel travel = Travel.builder()
                        .id(tr.getId())
                        .title(tr.getTitle())
                        .travelDate(tr.getTravelDate())
                        .travelType(tr.getTravelType())
                        .exclus(tr.getExclus())
                        .inclus(tr.getInclus())
                        .Activities(tr.getActivities())
                        .image(null)
                        .price(tr.getPrice())
                        .destiantion(tr.getDestiantion())
                        .nights(tr.getNights())
                        .days(tr.getDays())
                        .appUser(currentUser)
                        .build();
                travelService.save(travel);
            }

        }

        // Redirect to the travel list page
        return "redirect:/panel/admin/travels?success";
    }

    @GetMapping("/panel/admin/travels/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteClient(Long id){
        travelService.deleteTravel(id);
        return "redirect:/panel/admin/travels?successdelete";
    }


//  travel details
   @GetMapping("/panel/admin/travels/{id}")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public String detailsPage(@PathVariable("id") Long id, Model model){
        model.addAttribute("travel",travelService.getTravelById(id).get());
        return "pages/travels/travelDetails";
    }

    @GetMapping("/search/travel")
    public String searchPage(@RequestParam(value = "destination", required = false) String destination,
                             @RequestParam(value = "traveldate", required = false) LocalDate travelDate,
                             @RequestParam(value = "days", required = false) Integer days,Model model  ){
        Subscriber subscriber = new Subscriber();
        model.addAttribute("subscriber",subscriber);
        List<Travel> travels = travelService.searchravels(destination, travelDate, days);
        //List<Travel> travels = new ArrayList<>();
        model.addAttribute("travels",travels);
        System.out.println(travels);
//        return  "pages/travels/searchTravel";
        return  "pages/travels/searchTravel";
    }
    //Update travel
    @PostMapping("panel/admin/travels/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateTravel(Authentication authentication, @ModelAttribute("travel") @Valid Travel updatedTravel,
                               @RequestParam("file") MultipartFile image, BindingResult result, Model model,
                               @RequestParam(value = "specialOffer", required = false) Boolean specialOffer) throws IOException {
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
        Travel existingTravel = travelService.getTravelById(updatedTravel.getId()).get();

        // Update the properties of the existing Travel object
        existingTravel.setTitle(updatedTravel.getTitle());
        existingTravel.setTravelDate(updatedTravel.getTravelDate());
        existingTravel.setTravelType(updatedTravel.getTravelType());
        existingTravel.setExclus(updatedTravel.getExclus());
        existingTravel.setInclus(updatedTravel.getInclus());
        existingTravel.setActivities(updatedTravel.getActivities());
        existingTravel.setPrice(updatedTravel.getPrice());
        existingTravel.setDestiantion(updatedTravel.getDestiantion());
        existingTravel.setNights(updatedTravel.getNights());
        existingTravel.setDays(updatedTravel.getDays());
        existingTravel.setAppUser(currentUser);

        if (specialOffer == null) {
            existingTravel.setSpecialOffer(false);
        } else {
            existingTravel.setSpecialOffer(true);
        }

        if (!image.isEmpty()) {
            // Remove the old image file
            if (existingTravel.getImage() != null) {
                String oldImageFilePath = "src/main/resources/static/travel-images/" + existingTravel.getImage();
                FileUplaodUtil.deleteFile(oldImageFilePath);
            }

            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String filename = System.currentTimeMillis() + "_" + originalFilename;
            existingTravel.setImage(filename);

            String upload = "src/main/resources/static/travel-images";
            FileUplaodUtil.saveFile(upload, filename, image);
        }
        travelService.save(existingTravel);
        // Redirect to the travel list page
        return "redirect:/panel/admin/travels?successUpdate";
    }


}
