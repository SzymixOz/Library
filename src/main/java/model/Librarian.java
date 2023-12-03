package model;

import javax.persistence.Entity;

@Entity
public class Librarian extends User {
    protected int userID;
    private int room;


    public Librarian(String firstName, String lastName, String email, int room) {
        super(firstName, lastName, email);
        this.room = room;
    }
}
