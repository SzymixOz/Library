package pl.edu.agh.model.users;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
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
