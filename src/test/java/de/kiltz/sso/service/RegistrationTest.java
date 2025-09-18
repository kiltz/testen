package de.kiltz.sso.service;


import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.model.Konto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationTest {
    @Autowired
    private KontoService service;
    private KontoDao dao;

    String BASIC_MAIL = "F@kiltz.de";
    String BASIC_MAIL2 = "f@kiltz.de";



    @Test
    void testEmailCaseSensitivity() throws SSOValidationException {
        Konto setup = getDefaultKunde();
        setup.setEmail(BASIC_MAIL);
        assertDoesNotThrow( () -> service.neu(setup));
        Konto loginKonto = service.login(BASIC_MAIL.toLowerCase(), setup.getPasswort());
        assertNotNull(loginKonto);
        loginKonto = service.login(BASIC_MAIL.toUpperCase(), setup.getPasswort());
        assertNotNull(loginKonto);
    }

    @Test
    void testEmailEmptySpaceFront(){
        Konto setup = getDefaultKunde();
        setup.setEmail(" "+BASIC_MAIL2);
        assertDoesNotThrow( () -> {
            service.neu(setup);
        });
        var konto = service.holePerEmail(BASIC_MAIL2);
        assertNotNull(konto);
    }

    private Konto getDefaultKunde() {
        Konto k = new Konto();
        k.setEmail(BASIC_MAIL);
        k.setNachname("Testa");
        k.setVorname("Rossa");
        k.setPasswort("Keins1&nix");
        return k;
    }
}

