package pl.edu.agh.model.users;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Date joinDate;
    private Date expirationDate;
    private boolean isActive;

    public User() {}
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.joinDate = new Date();
        this.expirationDate = null;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isActive() {
        return isActive;
    }
}
