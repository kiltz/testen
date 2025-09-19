package de.kiltz.sso.rest.v1;

import de.kiltz.sso.model.Konto;
import de.kiltz.sso.service.KontoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "/dataCRUD.sql")
class KontoRestServiceTest {

    public static final String DEFAULT_EMAIL = "test2@testa.de";
    public static final String ALT_PASSWORT = "keins";
    public static final String NEUES_PASSWORT = "superkeins";

    @Autowired
    KontoRestService kontoRestService;
    @Autowired
    KontoService kontoService;

    @Test
    void editPassTest() {
        kontoRestService.updatePass(DEFAULT_EMAIL, ALT_PASSWORT, NEUES_PASSWORT);
        Konto k = kontoService.holePerEmail(DEFAULT_EMAIL);
        assertEquals(NEUES_PASSWORT, k.getPasswort());
    }

}
