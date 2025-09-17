package de.kiltz.sso.utils;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;
import java.util.stream.Stream;

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

    public static List<String> getTestDaten() {
        return List.of(
                "", // leer
                "1234567",  // ganz schlecht
                "keingrossbuchstabe",// ohne Zahl und ohne Sonderzeichen und ohne Großbuchstabe
                "ohneEineZahl",  // ohne Zahl und ohne Sonderzeichen
                "Ohne1SonderZeichen",     // nur ohne Sonderzeichen
                "mit1Und&",
                "mit1Und&"+"0".repeat(7)
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
