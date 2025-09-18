package de.kiltz.sso.service;

import de.kiltz.sso.model.Konto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KontoServiceEmailTest {
    public static final String EMAIL_ADRESSE = "derEmail@tester.de";
    public static final String EMAIL_ADRESSE2 = "derEmail2@tester.de";
    @Autowired
    private KontoService service;

    @Test
    void testRegisterMitLeerzeichen() {
        Konto k = getDefaultKunde();
        k.setEmail(EMAIL_ADRESSE+" ");
        // Das Anlegen eines Kontos führt nicht zu einem Fehler
        assertDoesNotThrow(() ->  service.neu(k));

        var konto = service.holePerEmail(EMAIL_ADRESSE);
        assertNotNull(konto);
    }
    @Test
    void testRegisterMitGrossUndKlein() throws SSOValidationException {
        Konto k = getDefaultKunde();
        k.setEmail(EMAIL_ADRESSE2);
        // Das Anlegen eines Kontos führt nicht zu einem Fehler
        assertDoesNotThrow(() ->  service.neu(k));

        Konto logKonto = service.login(EMAIL_ADRESSE2.toLowerCase(), k.getPasswort());
        assertNotNull(logKonto);
        logKonto = service.login(EMAIL_ADRESSE2.toUpperCase(), k.getPasswort());
        assertNotNull(logKonto);
    }


    private Konto getDefaultKunde() {
        Konto k = new Konto();
        k.setEmail(EMAIL_ADRESSE);
        k.setNachname("Testa");
        k.setVorname("Rossa");
        k.setPasswort("Keins1&nix");
        return k;
    }
}
