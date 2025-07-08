package de.kiltz.sso.utils;

public class TextUtils {

    public static boolean pruefePasswort(String passwort) {
        return passwort != null && passwort.length() >= 5;
    }
}
