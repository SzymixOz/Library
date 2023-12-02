package pl.edu.agh.validator;

import java.util.regex.Pattern;

public class UserValidator {

    private static boolean checkPattern(String word, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(word).matches();
    }
    public static boolean isMailValid(String mail) {
        String mailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return checkPattern(mail, mailRegex);
    }
    public static boolean isFirstNameValid(String mail) {
        String firstNameRegex = "^[A-Z][a-z]{1,29}$";
        return checkPattern(mail, firstNameRegex);
    }
    public static boolean isLastNameValid(String mail) {
        String lastNameRegex = "^[A-Z][a-zA-Z\\-']{1,29}$";
        return checkPattern(mail, lastNameRegex);
    }

}
