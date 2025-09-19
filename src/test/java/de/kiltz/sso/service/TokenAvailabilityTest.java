package de.kiltz.sso.service;

import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.data.KontoEntity;
import de.kiltz.sso.rest.v1.LoginRestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
@Transactional
 class TokenAvailabilityTest {
    @Autowired
    private LoginRestService service;
    @Autowired
    private KontoDao dao;

    @Test
    void testTokenAvailableLoginNoLogout(){
        KontoEntity k = getDefaultKonto();
        KontoEntity ktest = dao.save(k);
        ResponseEntity<String> firstLoginToken = assertDoesNotThrow(()->service.login(ktest.getEmail(), ktest.getPasswort()));

        ResponseEntity<String> secondLoginToken = assertDoesNotThrow(()->service.login(ktest.getEmail(), ktest.getPasswort()));

        assertEquals(firstLoginToken.getBody(),secondLoginToken.getBody());
    }

    private static KontoEntity getDefaultKonto() {
        KontoEntity e = new KontoEntity();
        e.setEmail("test3@testa.de");
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        return e;
    }
}
