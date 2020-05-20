package de.kiltz.sso.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.kiltz.sso.data.KontoEntity;

/**
 * @author tz
 */
@SpringBootTest
class KontoCRUDTest {

    @Autowired
    private KontoDao dao;

    @Test
    void testInsert(){
        KontoEntity e = new KontoEntity();
        e.setEmail("test@testa.de");
        e.setNachname("Rossa");
        e.setVorname("Testa");
        e.setPasswort("keins");
        assertNull(e.getId());

        KontoEntity e2 = dao.save(e);
        assertNotNull(e2.getId());
    }
}
