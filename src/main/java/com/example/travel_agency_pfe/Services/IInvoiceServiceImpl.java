package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.Invoice;
import com.example.travel_agency_pfe.Models.Reservation;
import com.example.travel_agency_pfe.Repositories.IInvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IInvoiceServiceImpl implements IInvoiceService {

    private IInvoiceRepository invoiceRepository;
    @Override
    public Invoice createNewInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findAllByReservation(Reservation reservation) {
        return invoiceRepository.getInvoiceByReservation(reservation);
    }

    @Override
    public void deleteinvoice(Long invoiceid) {
        invoiceRepository.deleteById(invoiceid);
    }
}
