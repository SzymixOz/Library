package pl.edu.agh.model.users;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    private int room;
    private String phoneNumber;

    public Admin(String firstName, String lastName, String email, String phoneNumber, int room) {
        super(firstName, lastName, email);
        this.phoneNumber = phoneNumber;
        this.room = room;
    }

    public Admin() {
    }

    public int getRoom() {
        return room;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
