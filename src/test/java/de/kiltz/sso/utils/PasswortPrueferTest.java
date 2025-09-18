package de.kiltz.sso.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
  Das Passwort darf
   nicht leer sein und
  muss mindestens 8 Zeichen enthalten,
   davon mindestens eine Zahl,
   ein Großbuchstabe und
   ein Sonderzeichen.
 */
class PasswortPrueferTest {


    String feinesPasswort = "mit1Und&"+"0".repeat(8);

    private static List<String> getTestDaten() {
        return List.of(
                "", // leer
                "1234567",  // ganz schlecht
                "keingrossbuchstabe",// ohne Zahl und ohne Sonderzeichen und ohne Großbuchstabe
                "k3ingrossbuchstabe",// ohne Zahl und ohne Sonderzeichen und ohne Großbuchstabe
                "ohneEineZahl",  // ohne Zahl und ohne Sonderzeichen
                "Ohne1SonderZeichen",     // nur ohne Sonderzeichen
                "mit1Ud&"
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDaten")
    void testeFalschePasswoerter(String pw) {
        assertFalse(TextUtils.pruefePasswort(pw));
    }

    @Test
    void testPasswortFehlerWeilNull() {
        assertFalse(TextUtils.pruefePasswort(null));
    }

    @Test
    void gueltigesPasswortTest() {
        assertTrue(TextUtils.pruefePasswort(feinesPasswort));
    }
}
