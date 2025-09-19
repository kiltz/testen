package de.kiltz.sso.rest.v1;

import de.kiltz.sso.service.SSOValidationException;
import de.kiltz.sso.service.SsoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//insert into konto (vorname, nachname, email, passwort) values ('Testa', 'Rossa', 'test2@testa.de', 'keins');
class LoginRestServiceTest {
    private static final String DEFAULT_MAIL = "test2@testa.de";
    private static final String DEFAULT_PASSWORT = "keins";
    @Autowired
    private LoginRestService restService;

    @Autowired
    private SsoService ssoService;

    @Test
    @Sql(scripts = "/dataCrud.sql")
    void testZweifachesLogin() throws SSOValidationException {
        ResponseEntity<String> resp1 = restService.login(DEFAULT_MAIL, DEFAULT_PASSWORT);
        ResponseEntity<String> resp2 = restService.login(DEFAULT_MAIL, DEFAULT_PASSWORT);
        // Hat das Login geklappt
        assertEquals(HttpStatus.OK, resp1.getStatusCode());
        assertEquals(HttpStatus.OK, resp2.getStatusCode());
        // sind die beiden Token gleich?
        assertEquals(resp1.getBody(), resp2.getBody());

    }

    @Test
    void createTokenTest() {
        String token1 = ssoService.getOrCreateToken(DEFAULT_MAIL);
        String token2 = ssoService.getOrCreateToken(DEFAULT_MAIL);
        // sind die beiden Token gleich?
        assertNotNull(token1);
        assertEquals(token1, token2);
    }

}
