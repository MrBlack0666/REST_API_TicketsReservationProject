/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import models.Flight;

/**
 *
 * @author Mateusz
 */
public class FlightDTO {
    private int id;
    private String to_City;
    private String from_City;
    private String flightDepartureDate;
    private String flightArrivalDate;
    private double price;
    private int numberOfSeats;
    private int numberOfAvaiableSeats;
    
    public FlightDTO(int id, String to_City, String from_City, String flightDepartureDate, String flightArrivalDate, double price, int numberOfSeats, int numberOfAvaiableSeats) {
        this.id = id;
        this.to_City = to_City;
        this.from_City = from_City;
        this.flightDepartureDate = flightDepartureDate;
        this.flightArrivalDate = flightArrivalDate;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.numberOfAvaiableSeats = numberOfAvaiableSeats;
    }
    
    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.to_City = flight.getTo_City();
        this.from_City = flight.getFrom_City();
        this.flightDepartureDate = flight.getFlightDepartureDate().toString();
        this.flightArrivalDate = flight.getFlightArrivalDate().toString();
        this.price = flight.getPrice();
        this.numberOfSeats = flight.getNumberOfSeats();
        this.numberOfAvaiableSeats = flight.getNumberOfAvaiableSeats();
    }
    
    public FlightDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTo_City() {
        return to_City;
    }

    public void setTo_City(String to_City) {
        this.to_City = to_City;
    }

    public String getFrom_City() {
        return from_City;
    }

    public void setFrom_City(String from_City) {
        this.from_City = from_City;
    }

    public String getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public void setFlightDepartureDate(String flightDepartureDate) {
        this.flightDepartureDate = flightDepartureDate;
    }

    public String getFlightArrivalDate() {
        return flightArrivalDate;
    }

    public void setFlightArrivalDate(String flightArrivalDate) {
        this.flightArrivalDate = flightArrivalDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfAvaiableSeats() {
        return numberOfAvaiableSeats;
    }

    public void setNumberOfAvaiableSeats(int numberOfAvaiableSeats) {
        this.numberOfAvaiableSeats = numberOfAvaiableSeats;
    }
    
    
}
