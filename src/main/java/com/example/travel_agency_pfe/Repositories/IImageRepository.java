package com.example.travel_agency_pfe.Repositories;

import com.example.travel_agency_pfe.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {
    Image findByName(String name);
}
