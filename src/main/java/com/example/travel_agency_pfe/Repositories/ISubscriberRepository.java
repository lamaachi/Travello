package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ISubscriberRepository extends JpaRepository<Subscriber,String> {

}
