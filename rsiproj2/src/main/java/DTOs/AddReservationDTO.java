/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.List;
import models.Flight;
import models.Person;

/**
 *
 * @author Mateusz
 */
public class AddReservationDTO {
    private int id;
    private int flightId;
    private Flight flightDTO;
    private List<PersonDTO> people;

    public AddReservationDTO(int id, int flightId, Flight flightDTO, List<PersonDTO> peopleDTO) {
        this.id = id;
        this.flightId = flightId;
        this.flightDTO = flightDTO;
        this.people = peopleDTO;
    }
    
    public AddReservationDTO(int id, int flightId, List<PersonDTO> peopleDTO) {
        this.id = id;
        this.flightId = flightId;
        this.flightDTO = null;
        this.people = peopleDTO;
    }
    
    public AddReservationDTO(int flightId, List<PersonDTO> peopleDTO) {
        this.id = 0;
        this.flightId = flightId;
        this.flightDTO = null;
        this.people = peopleDTO;
    }
    
    public AddReservationDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public Flight getFlightDTO() {
        return flightDTO;
    }

    public void setFlightDTO(Flight flightDTO) {
        this.flightDTO = flightDTO;
    }

    public List<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }
    
    
}
