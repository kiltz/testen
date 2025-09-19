package de.kiltz.sso.rest.v1;

import de.kiltz.sso.service.SSOValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "/dataPasswordChange.sql")
@Transactional
class PasswordChangeTest {

    private static final String DEFAULT_EMAIL = "test2@testa.de";
    private static final String DEFAULT_PASSWORD = "Eins$Me1ns!";
    private static final String NEW_PASSWORD = "Deins&Se1ns!";

    @Autowired
    KontoRestService service;

    @Test
    void testChangePasswordGoodDay() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), service.changePassword(DEFAULT_EMAIL, DEFAULT_PASSWORD, NEW_PASSWORD));

        //cleanup for next Test
        service.changePassword(DEFAULT_EMAIL, NEW_PASSWORD, DEFAULT_PASSWORD);
    }

    @Test
    void testIncorrectOldPassword() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), service.changePassword(DEFAULT_EMAIL, NEW_PASSWORD, NEW_PASSWORD));
    }

    @Test
    void testInvalidNewPassword() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), service.changePassword(DEFAULT_EMAIL, DEFAULT_PASSWORD, "keins"));
    }

    @Test
    void testInvalidEmail() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), service.changePassword("bad@email.org", NEW_PASSWORD, NEW_PASSWORD));
    }
}

