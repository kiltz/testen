package de.kiltz.sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import de.kiltz.sso.data.KontoEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tz
 */
@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
@Transactional
class KontoCRUDTest {

    @Autowired
    private KontoDao dao;
    private static KontoEntity getDefaultKonto(){
        KontoEntity e = new KontoEntity();
        e.setEmail("test@testa.de");
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        assertNull(e.getId());
        return e;
    }

    @Test
    void testInsert(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        assertNull(kontoUngespeichert.getId());
        KontoEntity kontoGespeichert = dao.save(kontoUngespeichert);
        assertNotNull(kontoGespeichert.getId());
    }
    @Test
    void testReadWithGetReferenceById(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontoGespeichert = dao.save(kontoUngespeichert);
        long id = kontoGespeichert.getId();
        KontoEntity reference = dao.getReferenceById(id);
        assertEquals("test@testa.de", reference.getEmail());
    }

    @Test
    void testUpdate(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontoGespeichert = dao.save(kontoUngespeichert);
        kontoGespeichert.setNachname("Neu");
        KontoEntity updated = dao.save(kontoGespeichert);
        assertEquals("Neu", updated.getNachname());
    }

    @Test
    void testDelete(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontoGespeichert = dao.save(kontoUngespeichert);
        Long id = kontoGespeichert.getId();
        dao.deleteById(id);
        Optional<KontoEntity> deleted = dao.findById(id);
        assertTrue(deleted.isEmpty());
    }
}
