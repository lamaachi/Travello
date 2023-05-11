package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Reservation;
import com.example.travel_agency_pfe.Repositories.IResevationRepository;
import org.springframework.stereotype.Service;

@Service

public class IReservationServiceImpl implements IReservationService {
    private IResevationRepository resevationRepository;
    @Override
    public Reservation save(Reservation reservation) {
        return resevationRepository.save(reservation);
    }
}
