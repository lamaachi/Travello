package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Repositories.ITravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ITravelServiceImpl implements ITravelService {
    @Autowired
    private ITravelRepository travelRepository;
    @Override
    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    @Override
    public void deleteTravel(Long id) {
        travelRepository.deleteById(id);
    }

    @Override
    public Travel save(Travel travel) {
        return travelRepository.save(travel);
    }

    @Override
    public Optional<Travel> getTravelById(Long id) {
        return travelRepository.findById(id);
    }

    @Override
    public List<Travel> searchravels(String destination, LocalDate travelDate, Integer days) {
        return travelRepository.findByDestiantionOrTravelDateOrDays(destination,travelDate,days);
    }
}
