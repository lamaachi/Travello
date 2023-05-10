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
                "Khouribga", "Beni Mellal", "Fes-Meknes", "Marrakesh-Safi", "Rabat-Sale-Kenitra",
                "Tanger-Tetouan-Al Hoceima", "Oriental", "Guelmim-Oued Noun", "Souss-Massa",
                "Beni Mellal-Khenifra", "Drâa-Tafilalet", "Casa-Settat", "Dakhla-Oued Ed-Dahab",
                "Laayoune-Sakia El Hamra"
        );
    }
    public static List<String> getCityList() {
        return getCities();
    }
}
