package de.kiltz.sso.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    public static boolean pruefePasswort(String passwort) {
        if (passwort == null || passwort.isEmpty()) {
            return false;  // Passwort darf nicht leer sein
        }

        if (passwort.length() < 8) {
            return false;  // Passwort muss mindestens 8 Zeichen lang sein
        }

        // Muss mindestens eine Zahl enthalten
        if (!Pattern.compile(".*\\d.*").matcher(passwort).matches()) {
            return false;
        }

        // Muss mindestens einen Großbuchstaben enthalten
        if (!Pattern.compile(".*[A-Z].*").matcher(passwort).matches()) {
            return false;
        }

        // Muss mindestens ein Sonderzeichen enthalten
        if (!Pattern.compile(".*[!@#$%^&*(),.?\":{}|<>].*").matcher(passwort).matches()) {
            return false;
        }

        return true;
    }

    public static boolean validateEmail(String email) {

        if (email == null || email.isEmpty()) {
            return false;
        }

        email = email.trim().toLowerCase();

        Pattern emailRegex = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        email = email.toLowerCase();
        Matcher matcher = emailRegex.matcher(email);
        return matcher.matches();
    }
}
