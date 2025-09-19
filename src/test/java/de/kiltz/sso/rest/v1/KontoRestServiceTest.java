package de.kiltz.sso.rest.v1;

import de.kiltz.sso.model.Konto;
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

@SpringBootTest
@Sql(scripts = "/dataCrud.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/loescheCrud.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class KontoRestServiceTest {
    private static final String DEFAULT_MAIL = "test2@testa.de";
    private static final String DEFAULT_PASSWORT = "keins";
    private static final String NEUES_PASSWORT = "mit1Und&";

    @Autowired
    private KontoRestService restService;
    @Autowired
    private KontoService kontoService;

    @Test
    void aenderePasswortTestOK() throws SSOValidationException {
        ResponseEntity resp = restService.updatePasswort(DEFAULT_MAIL, DEFAULT_PASSWORT, NEUES_PASSWORT);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());

        Konto k = kontoService.holePerEmail(DEFAULT_MAIL);
        assertEquals(NEUES_PASSWORT, k.getPasswort());

    }
    @Test
    void aenderePasswortTestFalschesPasswort() throws SSOValidationException {
        ResponseEntity resp = restService.updatePasswort(DEFAULT_MAIL, "FalschesPasswort", NEUES_PASSWORT);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());

    }
    @Test
    void aenderePasswortTestSchwachesNeuesPasswort() throws SSOValidationException {
        ResponseEntity resp = restService.updatePasswort(DEFAULT_MAIL, DEFAULT_PASSWORT, "keins");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());

    }
}
