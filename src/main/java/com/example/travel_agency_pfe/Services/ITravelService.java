package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Travel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ITravelService {
    List<Travel> getAllTravels();
    void deleteTravel(Long id);
    Travel save(Travel travel);
    Optional<Travel> getTravelById(Long id);
}
