package de.kiltz.sso.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {

    private static List<String> getTestCorrectEmails() {
        return List.of(
                "POSTfACH@meta.ua",
                "weB3t@gmail.com",
                "999lucky@hotmail.com",
                "Dylan@use.net",
                "S.dorn@web.de"
        );
    }

    private static List<String> getTestInCorrectEmails() {
        return List.of(
                "",
                "@",
                "@hotmail.com",
                "Dylan@.net",
                "S.dorn@web."
        );
    }

    @ParameterizedTest
    @MethodSource("getTestCorrectEmails")
    void testValidEmail(String email) {
        assertTrue(TextUtils.validateEmail(email));
    }

    @ParameterizedTest
    @MethodSource("getTestInCorrectEmails")
    void testInvalidEmail(String email) {
        assertFalse(TextUtils.validateEmail(email));
    }

}
