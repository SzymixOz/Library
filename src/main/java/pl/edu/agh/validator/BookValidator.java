package pl.edu.agh.validator;

import java.util.regex.Pattern;

public class BookValidator {

    private static boolean checkPattern(String word, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(word).matches();
    }
    public static boolean isTitleValid(String title) {
        String titleRegex = "^[A-Z][a-zA-Z0-9\\- ']{1,40}$";
        return checkPattern(title, titleRegex);
    }
    public static boolean isAuthorValid(String author) {
        String authorRegex = "^[A-Z][a-zA-Z\\- .']{1,40}$";
        return checkPattern(author, authorRegex);
    }
    public static boolean isIsbnValid(String isbn) {
        String isbnRegex = "^[0-9]{13}$";
        return checkPattern(isbn, isbnRegex);
    }
}
