package de.kiltz.sso.rest.v1;

import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SSOValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Sql(scripts = "/dataPasswordChange.sql")
@Transactional
class PasswordChangeTest {

    private static final String DEFAULT_EMAIL = "test2@testa.de";
    private static final String DEFAULT_PASSWORD = "Eins$Me1ns!";
    private static final String NEW_PASSWORD = "Deins&Se1ns!";

    @Autowired
    private KontoRestService kontoRestService;
    @Autowired
    private KontoService kontoService;

    @Test
    void testChangePasswordGoodDay() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), kontoRestService.changePassword(DEFAULT_EMAIL, DEFAULT_PASSWORD, NEW_PASSWORD));

        assertEquals(NEW_PASSWORD, kontoService.holePerEmail(DEFAULT_EMAIL).getPasswort());
        //cleanup for next Test
        kontoRestService.changePassword(DEFAULT_EMAIL, NEW_PASSWORD, DEFAULT_PASSWORD);
    }

    @Test
    void testIncorrectOldPassword() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), kontoRestService.changePassword(DEFAULT_EMAIL, NEW_PASSWORD, NEW_PASSWORD));

        assertNotEquals(NEW_PASSWORD, kontoService.holePerEmail(DEFAULT_EMAIL).getPasswort());
    }

    @Test
    void testInvalidNewPassword() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), kontoRestService.changePassword(DEFAULT_EMAIL, DEFAULT_PASSWORD, "keins"));
        assertNotEquals("keins", kontoService.holePerEmail(DEFAULT_EMAIL).getPasswort());
    }

    @Test
    void testInvalidEmail() throws SSOValidationException {
        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), kontoRestService.changePassword("bad@email.org", NEW_PASSWORD, NEW_PASSWORD));
        assertNotEquals(NEW_PASSWORD, kontoService.holePerEmail(DEFAULT_EMAIL).getPasswort());
    }
}

