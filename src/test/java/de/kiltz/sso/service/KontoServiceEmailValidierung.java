package de.kiltz.sso.service;

import de.kiltz.sso.model.Konto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
class KontoServiceEmailValidierungTest {

    @Autowired
    private KontoService service;

    Konto getCorrectKonto()
    {
        Konto konto = new Konto();
        konto.setId(1L);
        konto.setEmail("derTester@tt.de");
        konto.setPasswort("derTester#1");
        konto.setNachname("Tester");
        konto.setVorname("Tester");
        return konto;
    }

    Konto getIncorrectKonto()
    {
        Konto konto = new Konto();
        konto.setId(1L);
        konto.setEmail("derTester@.de");
        konto.setPasswort("derTester#1");
        konto.setNachname("Tester");
        konto.setVorname("Tester");
        return konto;
    }

    @Test
    void ValidEmailTest() {
        try {
            service.login("derTester@tt.de", "Hallo");
            service.neu(getCorrectKonto());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Sollte keine Ex werfen");
        }
    }

    @Test
    void InvalidEmailTest() {
        try {
            service.login("derTester@.de", "Hallo");
            fail("Sollte eine Ex werfen");
        } catch (Exception ignored) {
            // Alles gut
        }
        try {
            service.neu(getIncorrectKonto());
            fail("Sollte eine Ex werfen");
        }  catch (Exception ignored) {
            // Alles in Ordnung
        }
    }

}
