/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Flight;

/**
 *
 * @author Mateusz
 */
public class FlightService {
       private static Map<Integer, Flight> flights = new HashMap<Integer, Flight>();
    private static int Id = 1;

    public FlightService() {
        if(flights.isEmpty()) {
            Date date1 = new Date(120, 6, 20);
            date1.setHours(20);
            Date date2 = new Date(120, 6, 20);
            date2.setHours(22);
            flights.put(Id, new Flight(Id, "Barcelona", "Warszawa", date1, date2, 500.00, 50, 48));
            Id++;
        }
    }
    
    public List<Flight> getFlights() {
        return new ArrayList<Flight>(flights.values());
    }
    
    public Flight getFlightById(int flightId) {
        return flights.get(flightId);
    }
    
    public Flight addFlight(Flight flight) {
        if("".equals(flight.getFrom_City()) || "".equals(flight.getTo_City()) ||
                !flight.getFlightDepartureDate().after(new Date()) ||
                !flight.getFlightArrivalDate().after(flight.getFlightDepartureDate()) ||
                flight.getPrice() <= 0 || flight.getNumberOfSeats() <= 0) {
            return null;
        }
        
        flight.setId(Id);
        flight.setNumberOfAvaiableSeats(flight.getNumberOfSeats());
        flights.put(Id, flight);
        Id++;
        return flight;
    }
    
    public boolean editFlight(int flightId, Flight flight) {
        if("".equals(flight.getFrom_City()) || "".equals(flight.getTo_City()) ||
                !flight.getFlightDepartureDate().after(new Date()) ||
                !flight.getFlightArrivalDate().after(flight.getFlightDepartureDate()) ||
                flight.getPrice() <= 0 || flight.getNumberOfSeats() <= 0) {
            return false;
        }
        
        Flight modifiedFlight = flights.get(flightId);
        
        if(modifiedFlight == null) {
            return false;
        }
        
        int currentNumberOfAvaiableSeats = modifiedFlight.getNumberOfAvaiableSeats() - (modifiedFlight.getNumberOfSeats() - flight.getNumberOfSeats());
        
        if(currentNumberOfAvaiableSeats < 0) {
            return false;
        }
        
        flight.setNumberOfAvaiableSeats(currentNumberOfAvaiableSeats);
        
        modifiedFlight.updateFlight(flight);
        return true;
    }
    
    public boolean deleteFlight(int flightId) {
        Flight deletedFlight = flights.remove(flightId);
        
        return deletedFlight != null;
    }
    
    public boolean updateFlightNumberOfAvaiableSeats(int flightId, int numberOfReservedSeats) {
        Flight modifiedFlight = flights.get(flightId);
        
        if(modifiedFlight == null) {
            return false;
        }
        int avaiableSeats = modifiedFlight.getNumberOfAvaiableSeats() - numberOfReservedSeats;
        if(avaiableSeats < 0) {
            return false;
        }
        
        modifiedFlight.setNumberOfAvaiableSeats(avaiableSeats);
        return true;
    }
}
