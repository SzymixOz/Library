package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.repository.users.MemberRepository;
import pl.edu.agh.validator.UserValidator;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String addUser(String firstName, String lastName, String email) {
        if (!UserValidator.isFirstNameValid(firstName)) {
            return "Niepoprawne imie";
        }

        if (!UserValidator.isLastNameValid(lastName)) {
            return "Niepoprawne nazwisko";
        }

        if (!UserValidator.isMailValid(email)) {
            return "Niepoprawny mail";
        }

        Member member = new Member(firstName, lastName, email, false);
        try {
            memberRepository.save(member);
            return "Uzytkownik zostal dodany";
        } catch (Exception e) {
            return "Adres e-mail jest juz zajety";
        }
    }
}