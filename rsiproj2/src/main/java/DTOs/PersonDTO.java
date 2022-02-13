/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.Date;
import models.Person;

/**
 *
 * @author Mateusz
 */
public class PersonDTO {
    private int id;
    private String firstName;
    private String surname;
    private String birthDate;
    private int reservationId;

    public PersonDTO(int id, String firstName, String surname, String birthDate, int reservationId) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.birthDate = birthDate;
        this.reservationId = reservationId;
    }
    
    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.surname = person.getSurname();
        this.birthDate = person.getBirthDate().toString();
        this.reservationId = person.getReservationId();
    }
    
    public PersonDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    
    
}
