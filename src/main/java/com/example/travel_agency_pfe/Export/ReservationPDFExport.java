package com.example.travel_agency_pfe.Export;

import com.example.travel_agency_pfe.Models.Reservation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.util.List;


@Data @AllArgsConstructor @NoArgsConstructor
public class ReservationPDFExport {
    private List<Reservation> reservations;


    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Res_ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Travel_Id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Adults", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Childs", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Totale", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Client", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payed", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Reservation reservation : reservations) {
            table.addCell(String.valueOf(reservation.getId()));
            table.addCell(String.valueOf(reservation.getTravel().getId()));
            table.addCell(String.valueOf(reservation.getNumberOfAdults()));
            table.addCell(String.valueOf(reservation.getNumberOfChildren()));
            table.addCell(String.valueOf(reservation.getTotalAmount()));
            table.addCell(String.valueOf(reservation.getAppUser().getUserName()));
            if(reservation.getPayed()) {
                table.addCell("Yes");
            }else{
                table.addCell("NO");
            }
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.green);

        Paragraph p = new Paragraph("List of Reservations", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 1f, 1f, 1f, 1.5f,1.5f,1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
