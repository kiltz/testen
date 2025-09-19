package de.kiltz.sso.service;

import de.kiltz.sso.rest.v1.LoginRestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
//insert into konto (vorname, nachname, email, passwort) values ('Testa', 'Rossa', 'test2@testa.de', 'keins');
@Transactional
 class TokenAvailabilityTest {
    public static final String MAIL = "test2@testa.de";
    public static final String PASSWORT = "keins";
    @Autowired
    private LoginRestService service;


    @Test
    void testTokenAvailableLoginNoLogout(){
        ResponseEntity<String> firstLoginToken = assertDoesNotThrow(()->service.login(MAIL, PASSWORT));
        assertEquals(HttpStatus.OK, firstLoginToken.getStatusCode());

        ResponseEntity<String> secondLoginToken = assertDoesNotThrow(()->service.login(MAIL, PASSWORT));
        assertEquals(HttpStatus.OK, secondLoginToken.getStatusCode());
        assertEquals(firstLoginToken.getBody(),secondLoginToken.getBody());
    }


}
