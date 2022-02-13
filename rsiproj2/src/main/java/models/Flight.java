/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DTOs.FlightDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mateusz
 */
public class Flight {
    private int Id;
    private String To_City;
    private String From_City;
    private Date FlightDepartureDate;
    private Date FlightArrivalDate;
    private double Price;
    private int NumberOfSeats;
    private int NumberOfAvaiableSeats;
    
    public Flight() {}
    
    public Flight(int Id, String To_City, String From_City, Date FlightDepartureDate, Date FlightArrivalDate, double Price, int NumberOfSeats, int NumberOfAvaiableSeats) {
        this.Id = Id;
        this.To_City = To_City;
        this.From_City = From_City;
        this.FlightDepartureDate = FlightDepartureDate;
        this.FlightArrivalDate = FlightArrivalDate;
        this.Price = Price;
        this.NumberOfSeats = NumberOfSeats;
        this.NumberOfAvaiableSeats = NumberOfAvaiableSeats;
    }
    
    public Flight(FlightDTO flight) throws ParseException {
        this.Id = flight.getId();
        this.To_City = flight.getTo_City();
        this.From_City = flight.getFrom_City();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.FlightDepartureDate = formatter.parse(flight.getFlightDepartureDate());
        this.FlightArrivalDate = formatter2.parse(flight.getFlightArrivalDate());
        this.Price = flight.getPrice();
        this.NumberOfSeats = flight.getNumberOfSeats();
        this.NumberOfAvaiableSeats = flight.getNumberOfAvaiableSeats();
    }
    
    public int getId() {
        return Id;
    }
    
    public void setId(int Id) {
        this.Id = Id;
    }
    
    public String getTo_City() {
        return To_City;
    }
    
    public void setTo_City(String To_City) {
        this.To_City = To_City;
    }
    
    public String getFrom_City() {
        return From_City;
    }
    
    public void setFrom_City(String From_City) {
        this.From_City = From_City;
    }
    
    public Date getFlightDepartureDate() {
        return FlightDepartureDate;
    }
    
    public void setFlightDepartureDate(Date FlightDepartureDate) {
        this.FlightDepartureDate = FlightDepartureDate;
    }
    
    public Date getFlightArrivalDate() {
        return FlightArrivalDate;
    }
    
    public void setFlightArrivalDate(Date FlightArrivalDate) {
        this.FlightArrivalDate = FlightArrivalDate;
    }
    
    public double getPrice() {
        return Price;
    }
    
    public void setPrice(double Price) {
        this.Price = Price;
    }
    
    public int getNumberOfSeats() {
        return NumberOfSeats;
    }
    
    public void setNumberOfSeats(int NumberOfSeats) {
        this.NumberOfSeats = NumberOfSeats;
    }
    
    public int getNumberOfAvaiableSeats() {
        return NumberOfAvaiableSeats;
    }
    
    public void setNumberOfAvaiableSeats(int NumberOfAvaiableSeats) {
        this.NumberOfAvaiableSeats = NumberOfAvaiableSeats;
    }
    
    public void updateFlight(Flight flight) {
        this.To_City = flight.getTo_City();
        this.From_City = flight.getFrom_City();
        this.FlightDepartureDate = flight.getFlightDepartureDate();
        this.FlightArrivalDate = flight.getFlightArrivalDate();
        this.Price = flight.getPrice();
        this.NumberOfSeats = flight.getNumberOfSeats();
        this.NumberOfAvaiableSeats = flight.getNumberOfAvaiableSeats();
    }
}
