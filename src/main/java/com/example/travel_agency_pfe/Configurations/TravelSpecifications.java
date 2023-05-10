package com.example.travel_agency_pfe.Configurations;

import com.example.travel_agency_pfe.Models.Travel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TravelSpecifications {
//    public static Specification<Travel> searchTravels(String keyword,String destination, Integer minDays, Integer maxDays) {
//        return (root, query, criteriaBuilder) -> {
//            Predicate predicate = criteriaBuilder.conjunction();
//
//            if (keyword != null && !keyword.isEmpty()) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
//                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
//                        criteriaBuilder.like(root.get("activities"), "%" + keyword + "%")
//                ));
//            }
//
//            if (destination != null && !destination.isEmpty()) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("destination"), destination));
//            }
//
//            if (minDays != null) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("days"), minDays));
//            }
//
//            if (maxDays != null) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("days"), maxDays));
//            }
//
//            return predicate;
//        };
//    }
}
