package de.kiltz.sso.dao;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import de.kiltz.sso.data.KontoEntity;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tz
 */
@SpringBootTest
@Sql(scripts = "/dataCRUD.sql")
@Transactional
class KontoCRUDTest {

    @Autowired
    private KontoDao dao;
    String DEFAULT_MAIL = "test2@testa.de";

    private static KontoEntity getKontoEntity() {
        KontoEntity e = new KontoEntity();
        e.setEmail("test@testa.de");
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        return e;
    }

    @Test
    void testInsert(){
        KontoEntity e = getKontoEntity();
        assertNull(e.getId());

        KontoEntity e2 = dao.save(e);
        assertNotNull(e2.getId());
    }

    @Test
    void testReadViaID(){
        KontoEntity kontoUngespeichert = getKontoEntity();
        KontoEntity kontoGespeichert = dao.save(kontoUngespeichert);
        assertNotNull(kontoGespeichert.getId());

        KontoEntity reference= dao.getReferenceById(kontoGespeichert.getId());
        assertEquals(kontoGespeichert, reference);
    }

    @Test
    void testLogin(){
        KontoEntity found = dao.findByEmailAndPasswort(DEFAULT_MAIL,"keins");
        assertNotNull(found);
        assertEquals(DEFAULT_MAIL, found.getEmail());
        KontoEntity notFound = dao.findByEmailAndPasswort(DEFAULT_MAIL,"falsch");
        assertNull(notFound);
    }

    @Test
    void testReadViaSearchSurname(){
        List<KontoEntity> e = dao.findByNachnameContains("Rossa");
        assertEquals(1,e.size());
        KontoEntity entry = e.getFirst();
        assertNotNull(entry.getId());
        assertEquals(DEFAULT_MAIL,entry.getEmail());
    }

    @Test
    void testReadViaSearchMail() {
        KontoEntity e = dao.findByEmail(DEFAULT_MAIL);
        assertEquals("Testa", e.getVorname());
    }

    @Test
    void testChangeAndCleanSlateAfterDelete(){
        KontoEntity copy = getKontoEntity();
        copy.setEmail("delete@testa.de");
        dao.save(copy);
        assertEquals("delete@testa.de", copy.getEmail());

        Long id = copy.getId();
        assertNotNull(dao.findById(id));

        dao.deleteById(id);
        assertTrue(dao.findById(id).isEmpty());
    }


    //TO DO: Test für Lesen (per ID, über Suchmethoden), Ändern, Löschen
}
