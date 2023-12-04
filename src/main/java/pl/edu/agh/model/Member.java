package pl.edu.agh.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;

@Entity
public class Member extends User {
    private Boolean newsLetter;

    @Autowired
    public Member(String firstName, String lastName, String email, Boolean newsLetter) {
        super(firstName, lastName, email);
        this.newsLetter = newsLetter;
    }
    public Member() {
    }

    public Boolean getNewsLetter() {
        return newsLetter;
    }
}
