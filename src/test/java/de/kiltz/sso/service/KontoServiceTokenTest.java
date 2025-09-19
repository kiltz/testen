package de.kiltz.sso.service;


import de.kiltz.sso.SsoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SsoApplication.class)
@Sql(scripts = "/dataCrud.sql")
class KontoServiceTokenTest {

    @Autowired
    private SsoService ssoService;

    private static final String EMAIL = "testuser@example.com";

    @Test
    void tokenBleibtGleichOhneLogout() {
        // 1. Login
        String token1 = ssoService.createToken(EMAIL);
        assertNotNull(token1);

        // 2. Nochmal Login, ohne Logout
        String token2 = ssoService.createToken(EMAIL);
        assertNotNull(token2);

        // Erwartung: Beide Token sind gleich
        assertEquals(token1, token2, "Token sollte gleich bleiben, wenn kein Logout erfolgt");

        // 3. Jetzt Logout
        ssoService.delete(EMAIL, token1);

        // 4. Neuer Login → neuer Token
        String token3 = ssoService.createToken(EMAIL);
        assertNotNull(token3);
        assertNotEquals(token1, token3, "Token muss neu sein nach Logout");
    }

    /*
    KontoEntity k = getDefaultKonto();
    KontoEntity ktest = dao.save(k);

    ResponseEntity<String> firstLoginToken = assertDoesNotThrow(()->service.login(ktest.getEmail(), ktest.getPasswort()));

    ResponseEntity<String> secondLoginToken = assertDoesNotThrow(()->service.login(ktest.getEmail(), ktest.getPasswort()));

    assertEquals(firstLoginToken.getBody(),secondLoginToken.getBody());

    private Konto getDefaultKunde() {
        Konto k = new Konto();
        k.setEmail(EMAIL_ADRESSE);
        k.setNachname("Testa");
        k.setVorname("Rossa");
        k.setPasswort("Keins1&nix");
        return k;
    }


         */

}
