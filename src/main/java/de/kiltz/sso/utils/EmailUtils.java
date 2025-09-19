package de.kiltz.sso.utils;

public class EmailUtils {

    public static String normalisieren(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
