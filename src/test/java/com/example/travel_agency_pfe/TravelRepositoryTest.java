package com.example.travel_agency_pfe;

import com.example.travel_agency_pfe.Models.Travel;
import com.example.travel_agency_pfe.Repositories.ITravelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TravelRepositoryTest {
    @Autowired
    private ITravelRepository travelRepository;

    @Test
    public void testCreateTravel(){
    }
}
