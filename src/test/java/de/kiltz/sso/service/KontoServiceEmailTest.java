package de.kiltz.sso.service;


import de.kiltz.sso.model.Konto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KontoServiceEmailTest {

    public static final String EMAIL_ADRESSE = "der@tester.de";
    @Autowired
    private KontoService service;
    @Test
    void testRegistrierungMitEmailMitWhitespaceUndCase() {
       Konto k = getDefaultKunde();
       k.setEmail(EMAIL_ADRESSE+ " ");
       assertDoesNotThrow(() -> service.neu(k));

       var konto = service.holePerEmail(EMAIL_ADRESSE);
       assertNotNull(konto);
    }

    void testMitRegisterMitGrossUndKlein() throws SSOValidationException{
        Konto k = getDefaultKunde();
        k.setEmail(EMAIL_ADRESSE);

        assertDoesNotThrow(()-> service.neu(k));

        Konto logKonto = service.login(EMAIL_ADRESSE.toLowerCase(), k.getPasswort());
        assertNotNull(logKonto);
        logKonto = service.login(EMAIL_ADRESSE.toUpperCase(),k.getPasswort());
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
