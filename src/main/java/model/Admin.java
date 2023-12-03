package model;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    protected int userID;
    private int room;
    private String phoneNumber;


    public Admin(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email);
        this.phoneNumber = phoneNumber;
    }
}
