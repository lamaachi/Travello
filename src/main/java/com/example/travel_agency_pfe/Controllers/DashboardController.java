package com.example.travel_agency_pfe.Controllers;


import com.example.travel_agency_pfe.DTO.TotalAmountPerMonthDto;
import com.example.travel_agency_pfe.Repositories.IInvoiceRepository;
import com.example.travel_agency_pfe.Repositories.ITravelRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Year;
import java.util.List;

@Controller
@AllArgsConstructor
public class DashboardController {
    private IInvoiceRepository invoiceRepository;

    @RequestMapping("/panel/invoices/totalAmountPerMonth")
    @ResponseBody
    public String getTotalAmountPerMonth() {
        Year currentYear = Year.now();
        int year = currentYear.getValue();
        List<Object[]> totalAmountPerMonth = invoiceRepository.getTotalAmountPerMonth(year);
        JsonArray jsonMonths = new JsonArray();
        JsonArray jsonAmounts = new JsonArray();
        JsonObject json = new JsonObject();

        totalAmountPerMonth.forEach(data->{
            int month = (int) data[0];
            double amount = (double) data[1];

            jsonMonths.add(month);
            jsonAmounts.add(amount);
        });

        json.add("month",jsonMonths);
        json.add("amount",jsonAmounts);
        return json.toString();
    }
}
