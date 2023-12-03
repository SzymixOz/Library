package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int userID;

    protected String firstName;
    protected String lastName;
    protected String email;
    protected Date joinDate;
    protected Date expirationDate;
    protected boolean isActive;

    // stwórz konstruktor wpisujący wszystkie dane
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.joinDate = new Date();
        this.expirationDate = null;
        this.isActive = true;
    }



}
