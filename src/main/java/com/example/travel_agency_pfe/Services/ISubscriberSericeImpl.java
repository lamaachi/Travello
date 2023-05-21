package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Subscriber;
import com.example.travel_agency_pfe.Repositories.ISubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISubscriberSericeImpl implements ISubscriberSerice {
    @Autowired
    private ISubscriberRepository subscriberRepository;
    @Override
    public Subscriber addSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }
}
