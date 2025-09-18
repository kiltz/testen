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

    @Test
    void testAendernUndLoeschen() {
        String neuesPaswwort = "neuesPasswort";

        KontoEntity e = getDefaultKonto();
        KontoEntity e2 = dao.save(e);
        assertEquals(e.getPasswort(), e2.getPasswort());

        e2.setPasswort(neuesPaswwort);

        dao.save(e2);

        KontoEntity kontoGeholtPerId = dao.getReferenceById(e2.getId());
        assertEquals(neuesPaswwort, kontoGeholtPerId.getPasswort());

        dao.delete(kontoGeholtPerId);
        var erg = dao.findByEmailIgnoreCase(e2.getEmail());
        assertNull(erg);

    }


    @Test
    void testLogin() {
        KontoEntity gefunden = dao.findByEmailIgnoreCaseAndPasswort(DEFAULT_MAIL, "keins");
        assertEquals(DEFAULT_MAIL, gefunden.getEmail());

        KontoEntity nichtGefunden = dao.findByEmailIgnoreCaseAndPasswort(DEFAULT_MAIL, "falsch");
        assertNull(nichtGefunden);
    }
    @Test
    void testFindByEmail() {
        KontoEntity gefunden = dao.findByEmailIgnoreCase(DEFAULT_MAIL);
        assertEquals(DEFAULT_MAIL, gefunden.getEmail());

        KontoEntity nichtGefunden = dao.findByEmailIgnoreCase(DEFAULT_MAIL+"nichtDa");
        assertNull(nichtGefunden);
    }

    @Test
    @Sql(scripts = "/dataCrudFindByNachname.sql")
    void testFindByNachname() {
        var ergebnis = dao.findByNachnameContainsIgnoreCase("c");
        assertEquals(2, ergebnis.size());
        ergebnis = dao.findByNachnameContainsIgnoreCase("a");
        assertEquals(1, ergebnis.size());
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
        KontoEntity kGefunden = dao.findByEmailIgnoreCase("test2@testa.de");
        assertNotNull(kGefunden);
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
