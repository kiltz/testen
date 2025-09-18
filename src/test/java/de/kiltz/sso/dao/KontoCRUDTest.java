package de.kiltz.sso.dao;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.kiltz.sso.data.KontoEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tz
 */
@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
@Transactional
class KontoCRUDTest {

    private static final String DEFAULT_MAIL = "test@testa.de";
    private static final String DEFAUL_WRONG = "falsch";

    private static final String DEFAULT_MAIL2 = "test2@testa.de";
    @Autowired
    private KontoDao dao;

    private static KontoEntity getDefaultKonto() {
        KontoEntity kontoNeu = new KontoEntity();
        kontoNeu.setEmail(DEFAULT_MAIL);
        kontoNeu.setNachname("Rossa");
        kontoNeu.setVorname("Testa");
        kontoNeu.setPasswort("keins");
        return kontoNeu;
    }

    @Test
    void testHolePerID(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontogespeichert = dao.save(kontoUngespeichert);

        KontoEntity kontoGeholtPerID = dao.getReferenceById(kontogespeichert.getId());
        assertEquals(kontoGeholtPerID, kontogespeichert);

    }

    @Test
    void testFindByEmail(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        dao.save(kontoUngespeichert);

        assertNotNull(dao.findByEmail(DEFAULT_MAIL));
        assertNull(dao.findByEmail(DEFAUL_WRONG));
    }

    @Test
    void testFindByEmailAndPassword(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        dao.save(kontoUngespeichert);

        assertNotNull(dao.findByEmailAndPasswort(DEFAULT_MAIL, "keins"));
        assertNull(dao.findByEmailAndPasswort(DEFAULT_MAIL, "falsch"));
        assertNull(dao.findByEmailAndPasswort(DEFAUL_WRONG, "keins"));
        assertNull(dao.findByEmailAndPasswort(DEFAUL_WRONG, DEFAUL_WRONG));
    }

    @Test
    void testFindByName(){
        List<KontoEntity> emptyList = new ArrayList<>();

        assertNotNull(dao.findByNachnameContainsIgnoreCase("Rossa"));
        assertEquals(emptyList, dao.findByNachnameContainsIgnoreCase(DEFAUL_WRONG));
    }

    @Test
    void testDelete() {
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontogespeichert = dao.save(kontoUngespeichert);
        assertNotNull(dao.findByEmail(DEFAULT_MAIL));

        dao.delete(kontogespeichert);

        assertNull(dao.findByEmail(DEFAULT_MAIL));
    }
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
        var erg = dao.findByEmail(e2.getEmail());
        assertNull(erg);

    }


    @Test
    void testLogin() {
        KontoEntity gefunden = dao.findByEmailAndPasswort(DEFAULT_MAIL2, "keins");
        assertEquals(DEFAULT_MAIL2, gefunden.getEmail());

        KontoEntity nichtGefunden = dao.findByEmailAndPasswort(DEFAULT_MAIL2, "falsch");
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
        KontoEntity kGefunden = dao.findByEmail("test2@testa.de");
        assertNotNull(kGefunden);
    }

    @Test
    void testRead(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontogespeichert = dao.save(kontoUngespeichert);

        assertEquals(DEFAULT_MAIL, kontogespeichert.getEmail());
    }

}
