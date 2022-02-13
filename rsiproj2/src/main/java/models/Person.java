/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author Mateusz
 */
public class Person {
    private int Id;
    private String FirstName;
    private String Surname;
    private Date BirthDate;
    private int ReservationId;
    
    public Person() {}
    public Person(int Id, String FirstName, String Surname, Date BirthDate, int ReservationId) {
        this.Id = Id;
        this.FirstName = FirstName;
        this.Surname = Surname;
        this.BirthDate = BirthDate;
        this.ReservationId = ReservationId;
    }
    
    public int getId() {
        return Id;
    }
    
    public void setId(int Id) {
        this.Id = Id;
    }
    
    public String getFirstName() {
        return FirstName;
    }
    
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    
    public String getSurname() {
        return Surname;
    }
    
    public void setSurname(String Surname) {
        this.Surname = Surname;
    }
    
    public Date getBirthDate() {
        return BirthDate;
    }
    
    public void setBirthDate(Date BirthDate) {
        this.BirthDate = BirthDate;
    }
    
    public int getReservationId() {
        return ReservationId;
    }
    
    public void setReservationId(int ReservationId) {
        this.ReservationId = ReservationId;
    }
}
