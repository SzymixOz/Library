package model;

import javax.persistence.Entity;

@Entity
public class Member extends User {
    protected int userID;
    private Boolean newsLetter;

    public Member(String firstName, String lastName, String email, Boolean newsLetter) {
        super(firstName, lastName, email);
        this.newsLetter = newsLetter;
    }

}
