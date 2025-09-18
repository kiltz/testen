package de.kiltz.sso.service;

import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.model.Konto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql(scripts = "/dataEMail.sql")
@Transactional
class EMailServiceTest {

    private static final String DEFAULT_PASSWORD = "Keins&Me1ns";
    private static final String DEFAULT_MAIL = "test2@testa.de";

    @Autowired
    private KontoDao dao;

    @Autowired
    private KontoService service;

    @Test
    void testLoginMitStandardEMail() throws SSOValidationException {
        Konto testKonto = service.login(DEFAULT_MAIL, DEFAULT_PASSWORD);
        assertNotNull(testKonto);
    }

    @ParameterizedTest
    @MethodSource("getEMailAdressen")
    void testeLoginGueltigeAlternativeEMail(String eMail) throws SSOValidationException {
        Konto testKonto = service.login(eMail, DEFAULT_PASSWORD);
        assertNotNull(testKonto);
    }

    @Test
    void testRegistrierungNeueEMail() throws SSOValidationException {
        Konto neuKonto = new Konto();
        neuKonto.setEmail("test@Hotmail.com");
        neuKonto.setPasswort(DEFAULT_PASSWORD);
        assertNotNull(service.neu(neuKonto));
    }

    @ParameterizedTest
    @MethodSource("getEMailAdressen")
    void testRegistrierungAlternativeEMail(String eMail) {
        Konto neuKonto = new Konto();
        neuKonto.setEmail(eMail);
        neuKonto.setPasswort(DEFAULT_PASSWORD);
        try {
            service.neu(neuKonto);
            fail("EMail ist bereits vergeben");
        } catch (SSOValidationException e) {
            //Alles gut
        }
    }

    private static List<String> getEMailAdressen() {
        return List.of(
                DEFAULT_MAIL + " ",
                "Test2@testa.de",
                "test2@Testa.de",
                "teSt2@teSta.de ");
    }
}
