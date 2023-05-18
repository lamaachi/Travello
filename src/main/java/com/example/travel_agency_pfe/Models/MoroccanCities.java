package com.example.travel_agency_pfe.Models;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class MoroccanCities {
    public static List<String> getCities() {
        return Arrays.asList(
                "Agadir", "Casablanca", "Fes", "Marrakech", "Rabat",
                "Tangier", "Meknes", "Oujda", "Kenitra", "Tetouan",
                "Safi", "Sale", "Nador", "Al Hoceima", "Mohammedia",
                "Khouribga", "Beni Mellal", "Fes","Guelmim",
                "Khenifra",  "Dakhla",
                "Laayoune"
        );
    }
    public static List<String> getCityList() {
        return getCities();
    }
}
