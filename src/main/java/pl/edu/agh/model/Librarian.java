package pl.edu.agh.model;

import javax.persistence.Entity;

@Entity
public class Librarian extends User {
    private int room;

    public Librarian(String firstName, String lastName, String email, int room) {
        super(firstName, lastName, email);
        this.room = room;
    }

    public Librarian() {
    }

    public int getRoom() {
        return room;
    }
}
