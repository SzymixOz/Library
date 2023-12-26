package pl.edu.agh.model.users;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Librarian extends User {

    private int room;

    public Librarian(String firstName, String lastName, String email, int room, String password) {
        super(firstName, lastName, email, password);
        this.room = room;
    }

    public Librarian() {
    }

    public int getRoom() {
        return room;
    }
}
