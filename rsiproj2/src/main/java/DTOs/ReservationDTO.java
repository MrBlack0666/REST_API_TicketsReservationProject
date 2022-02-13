/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.ArrayList;
import java.util.List;
import models.Flight;
import models.Link;
import models.Person;

/**
 *
 * @author Mateusz
 */
public class ReservationDTO {
    private int id;
    private int flightId;
    private Flight flightDTO;
    private List<Person> people;
    private List<Link> links;
    
    public ReservationDTO() {
        people = new ArrayList<>();
        links = new ArrayList<>();
    }
    
    public ReservationDTO(int Id, int FlightId, Flight Flight, List<Person> People) {
        this.id = Id;
        this.flightId = FlightId;
        this.flightDTO = Flight;
        this.people = People;
        links = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int FlightId) {
        this.flightId = FlightId;
    }

    public Flight getFlightDTO() {
        return flightDTO;
    }

    public void setFlightDTO(Flight Flight) {
        this.flightDTO = Flight;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> People) {
        this.people = People;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
    public void addLink(Link link) {
        this.links.add(link);
    }
}
