package de.kiltz.sso.service;

import de.kiltz.sso.model.Konto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KontoServiceTest {

    @Autowired
    private KontoService service;

    @Test
    void testGoodDaySzenario() {
        // ich lege ein neues Konto an
        Konto k = getDefaultKunde();
        try {
            service.neu(k);
        } catch (SSOValidationException e) {
            fail("Sollte keine Ex werfen", e);
        }
        // ich logge mich mit den richten Daten ein
        try {
            Konto eingelogged = service.login(k.getEmail(), k.getPasswort());
            assertNotNull(eingelogged);
            // Das Konto was ich durch Login bekomme hat die gleichen Inhalte wie das Konto das ich angelegt habe
            pruefeGleichheitVon(k, eingelogged);
        } catch (SSOValidationException e) {
            fail("Sollte keine Ex werfen", e);
        }

        // Ich kann das Konto holem
        Konto geholt = service.holePerEmail(k.getEmail());
        // Das Konto was ich durchs holen bekomme hat die gleichen Inhalte wie das Konto das ich angelegt habe
        pruefeGleichheitVon(k, geholt);
        // Ich lösche das Konto
        service.loesche(geholt);
        // Login schlägt fehl
        try {
            Konto eingelogged = service.login(k.getEmail(), k.getPasswort());
            assertNull(eingelogged);
        } catch (SSOValidationException e) {
            fail("Sollte keine Ex werfen", e);
        }


    }

    private void pruefeGleichheitVon(Konto k, Konto eingelogged) {
        assertEquals(k.getEmail(), eingelogged.getEmail());
        assertEquals(k.getNachname(), eingelogged.getNachname());
        assertEquals(k.getVorname(), eingelogged.getVorname());
        assertEquals(k.getPasswort(), eingelogged.getPasswort());
    }

    private Konto getDefaultKunde() {
        Konto k = new Konto();
        k.setEmail("der@tester.de");
        k.setNachname("Testa");
        k.setVorname("Rossa");
        k.setPasswort("Keins1&nix");
        return k;
    }


}
