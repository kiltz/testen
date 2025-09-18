package de.kiltz.sso.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.kiltz.sso.data.KontoEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tz
 */
@SpringBootTest
@Sql(scripts = "/dataCrud.sql",  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class KontoCRUDTest {

    public static final String DEFAULT_EMAIL = "test@testa.de";

    @Autowired
    private KontoDao dao;

    @AfterEach
    void afterEach() {
        dao.deleteAll();
    }

    private KontoEntity getDefaultKonto() {
        KontoEntity e = new KontoEntity();
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        e.setEmail(DEFAULT_EMAIL);
        return e;
    }

    //region CREATE
    @Test
    void testInsert(){
        KontoEntity e = new KontoEntity();
        e.setEmail(DEFAULT_EMAIL);
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        assertNull(e.getId());

        KontoEntity e2 = dao.save(e);
        assertNotNull(e2.getId());
    }
    //endregion CREATE

    //region READ
    @Test
    void testFindById() {
        KontoEntity kontoUngespeicherrt = getDefaultKonto();
        KontoEntity kontoGespeicherrt = dao.save(kontoUngespeicherrt);

        assertNotNull(kontoGespeicherrt.getId());

        KontoEntity kontoByID = dao.getReferenceById(kontoGespeicherrt.getId());
        assertNotNull(kontoByID);
    }


    /**
     * Sucht nach der E-Mail aus dataCrud.sql
     */
    @Test
    void testFindByEmail(){
        KontoEntity ke = dao.findByEmail("test2@testa.de");
        assertNotNull(ke);
    }

    @Test
    void testFindByNachname() {
        List<KontoEntity> lke = dao.findByNachnameContainsIgnoreCase("Rossa");
        KontoEntity ke = lke.getFirst();
        assertNotNull(ke);
    }

    @Test
    void testFindByPasswortAndEmail() {
        KontoEntity ke = dao.findByEmailAndPasswort(DEFAULT_EMAIL, "keins");
        assertNotNull(ke);
        assertEquals(DEFAULT_EMAIL, ke.getEmail());
        assertEquals("keins", ke.getPasswort());
    }
    //endregion READ

    //region UPDATE&DELETE

    @Test
    void testUpdateAndDelete() {
        KontoEntity e = getDefaultKonto();
        KontoEntity e2 = dao.save(e);
        assertNotNull(e.getPasswort(), e2.getPasswort());

        e2.setPasswort("neuesPasswort");
        dao.save(e2);

        KontoEntity kontoGeholtPerID = dao.getReferenceById(e2.getId());
        assertEquals("neuesPasswort", kontoGeholtPerID.getPasswort());

        dao.delete(kontoGeholtPerID);
        var erg = dao.findByEmail(e2.getEmail());
        assertNull(erg);
    }
    //endregion UPDATE&DELETE
}
