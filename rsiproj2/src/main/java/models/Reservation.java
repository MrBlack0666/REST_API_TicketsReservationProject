/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DTOs.AddReservationDTO;
import DTOs.PersonDTO;
import DTOs.ReservationDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public class Reservation {
    private int Id;
    private int FlightId;
    private List<Person> People;
    
    public Reservation() {
        this.People = new ArrayList<Person>();
    }
    
    public Reservation(int Id, int FlightId) {
        this.Id = Id;
        this.FlightId = FlightId;
        this.People = new ArrayList<Person>();
    }
    
    public Reservation(int Id, int FlightId, List<Person> People) {
        this.Id = Id;
        this.FlightId = FlightId;
        this.People = People;
    }
    
    public Reservation(AddReservationDTO addReservationDTO) throws ParseException {
        this.Id = addReservationDTO.getId();
        this.FlightId = addReservationDTO.getFlightId();

        List<Person> people = new ArrayList<Person>();
        
        for(PersonDTO person : addReservationDTO.getPeople()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = formatter.parse(person.getBirthDate());
            
            people.add(new Person(person.getId(), person.getFirstName(), person.getSurname(), birthDate, person.getReservationId()));
        }
        
        this.People = people;
    }
    
    public int getId() {
        return Id;
    }
    
    public void setId(int Id) {
        this.Id = Id;
    }
    
    public int getFlightId() {
        return FlightId;
    }
    
    public void setFlightId(int FlightId) {
        this.FlightId = FlightId;
    }
    
    public List<Person> getPeople() {
        return People;
    }
    
    public void setPeople(List<Person> People) {
        this.People = People;
    }
}
