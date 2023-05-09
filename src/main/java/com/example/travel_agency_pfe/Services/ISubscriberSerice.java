package com.example.travel_agency_pfe.Services;


import com.example.travel_agency_pfe.Models.Subscriber;
import org.springframework.stereotype.Service;

@Service
public interface ISubscriberSerice {
    Subscriber addSubscriber(Subscriber subscriber);
}
