/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DTOs.ReservationDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Flight;
import models.Person;
import models.Reservation;

/**
 *
 * @author Mateusz
 */
public class ReservationService {
    private FlightService flightService;
    
    private static Map<Integer, Reservation> reservations = new HashMap<Integer, Reservation>();
    private static int ReservationId = 1;
    private static int PersonId = 1;
    
    public ReservationService() {
        flightService = new FlightService();
        if(reservations.isEmpty()) {
            List<Person> people = new ArrayList<Person>();
            Date date1 = new Date(1997, 9, 24);
            Date date2 = new Date(1997, 5, 11);
            
            people.add(new Person(PersonId, "Mateusz", "Czarniecki", date1, ReservationId));
            PersonId++;
            people.add(new Person(PersonId, "Adam", "Beton", date2, ReservationId));
            PersonId++;
            
            reservations.put(ReservationId, new Reservation(ReservationId, 1, people));
            ReservationId++;
        }
    }
    
    public ReservationDTO getReservationById(int reservationId) {
        Reservation reservation = reservations.get(reservationId);
        
        if(reservation == null) {
            return null;
        }
        
        Flight flight = flightService.getFlightById(reservation.getFlightId());
        
        if(flight == null) {
            return null;
        }
        
        return new ReservationDTO(reservation.getId(), reservation.getFlightId(), flight, reservation.getPeople());
    }
    
        public int addReservation(Reservation reservation) {
        Flight flight = flightService.getFlightById(reservation.getFlightId());
        
        if(flight == null || flight.getNumberOfAvaiableSeats() < reservation.getPeople().size() || reservation.getPeople() == null || reservation.getPeople().isEmpty()) {
            return -1;
        }
        
        reservation.setId(ReservationId);
        
        reservation.getPeople().forEach(person -> { 
            person.setReservationId(ReservationId);
            person.setId(PersonId);
            PersonId++;
        });
        
        reservations.put(ReservationId, reservation);
        ReservationId++;
        
        flight.setNumberOfAvaiableSeats(flight.getNumberOfAvaiableSeats() - reservation.getPeople().size());
        
        return ReservationId - 1;
    }
    
    public boolean deleteReservation(int reservationId) {
        Reservation reservation = reservations.remove(reservationId);
        
        if(reservation == null) {
            return false;
        }
        
        Flight flight = flightService.getFlightById(reservation.getFlightId());
        
        if(flight == null) {
            return false;
        }
        
        flight.setNumberOfAvaiableSeats(flight.getNumberOfAvaiableSeats() + reservation.getPeople().size());
        
        return true;
    }
    
        public byte[] getReservationConfirmationPdf(int reservationId) throws IOException, DocumentException {
        Reservation reservation = reservations.get(reservationId);
        
        if(reservation == null) {
            return null;
        }
        
        Flight flight = flightService.getFlightById(reservation.getFlightId());
        
        if(flight == null) {
            return null;
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, BaseColor.BLACK);
        Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

        document.add(new Paragraph("Potwierdzenie rezerwacji", font));
        document.add(new Paragraph("Nr rezerwacji: " + reservation.getId(), font1));
        document.add(new Paragraph("Lot z: " + flight.getFrom_City(), font1));
        document.add(new Paragraph("Lot do: " + flight.getTo_City(), font1));
        document.add(new Paragraph("Data odlotu: " + flight.getFlightDepartureDate().toString(), font1));
        document.add(new Paragraph("Data przylotu: " + flight.getFlightArrivalDate(), font1));
        document.add(new Paragraph("Osoby:", font1));
        
        for(Person person : reservation.getPeople()) {
            document.add(new Paragraph(person.getFirstName() + " " + person.getSurname() + ", urodzony " + person.getBirthDate(), font1));
        }
        
        document.add(new Paragraph("Cena: " + flight.getPrice() * reservation.getPeople().size() + " PLN", font1));
        
        document.close();

        return outputStream.toByteArray();
    }
}
