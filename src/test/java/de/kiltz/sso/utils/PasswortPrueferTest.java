package de.kiltz.sso.utils;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswortPrueferTest {

    @Test
    void testePasswortLaenge() {
        String passwort = "kurz";
        assertFalse(TextUtils.pruefePasswort(passwort));
        passwort = "länger";
        assertTrue(TextUtils.pruefePasswort(passwort));
    }
    @Test
    void testePasswortGrossUndKlein() {
        String passwort = "nurkleinbuchstaben";
        assertFalse(TextUtils.pruefePasswort(passwort));
        passwort = "mitGrossbuchstabe";
        assertTrue(TextUtils.pruefePasswort(passwort));
    }
}
