package de.kiltz.sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.kiltz.sso.data.KontoEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tz
 */
@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
@Transactional
class KontoCRUDTest {

    private static final String DEFAULT_MAIL = "test2@testa.de";
    @Autowired
    private KontoDao dao;

    @Test
    void testInsert(){
        KontoEntity e = getDefaultKonto();
        assertNull(e.getId());

        KontoEntity e2 = dao.save(e);
        assertNotNull(e2.getId());

    }


    /*
     TODO: Test für
     * Lesen
        - per id dao.getReferenceById()
        - über die 3 SuchMethoden findBy...
     * Ändern dao.save(..)
     * Löschen dao.delete(..)

    */

    @Test
    void testLogin() {
        KontoEntity gefunden = dao.findByEmailAndPasswort(DEFAULT_MAIL, "keins");
        assertEquals(DEFAULT_MAIL, gefunden.getEmail());

        KontoEntity nichtGefunden = dao.findByEmailAndPasswort(DEFAULT_MAIL, "falsch");
        assertNull(nichtGefunden);
    }

    @Test
    void testHolePerId() {
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontoGespeichert = dao.save(kontoUngespeichert);
        assertNotNull(kontoGespeichert.getId());

        KontoEntity kontoGeholtPerId = dao.getReferenceById(kontoGespeichert.getId());

        assertEquals(kontoGeholtPerId.getEmail(), kontoGespeichert.getEmail());
        assertEquals(kontoGeholtPerId, kontoGespeichert);
    }
    
    @Test
    void testHoleBeispielSatz() {
        KontoEntity kGefunden = dao.findByEmail("test2@testa.de");
        assertNotNull(kGefunden);
    }

    private static KontoEntity getDefaultKonto() {
        KontoEntity e = new KontoEntity();
        e.setEmail("test@testa.de");
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        return e;
    }
}
