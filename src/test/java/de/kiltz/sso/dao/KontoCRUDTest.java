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
    void testDelete(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontogespeichert = dao.save(kontoUngespeichert);
        assertNotNull(dao.findByEmail(DEFAULT_MAIL));

        dao.delete(kontogespeichert);

        assertNull(dao.findByEmail(DEFAULT_MAIL));
    }

    @Test
    void testRead(){
        KontoEntity kontoUngespeichert = getDefaultKonto();
        KontoEntity kontogespeichert = dao.save(kontoUngespeichert);

        assertEquals(DEFAULT_MAIL, kontogespeichert.getEmail());
    }

}
