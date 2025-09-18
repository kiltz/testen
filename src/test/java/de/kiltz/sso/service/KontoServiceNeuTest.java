package de.kiltz.sso.service;


import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.data.KontoEntity;
import de.kiltz.sso.model.Konto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author tz
 */
class KontoServiceNeuTest {

    private KontoService service;

    @BeforeEach
    void init() {
        KontoDao dao = mock(KontoDao.class);
        when(dao.save(any(KontoEntity.class))).then(i -> {
            KontoEntity k = i.getArgument(0, KontoEntity.class);
            k.setId(1L);
            return k;
        });

        KontoEntity kontoEntity = new KontoEntity();
        kontoEntity.setEmail("f@kiltz.de");
        kontoEntity.setPasswort("keins");
        when((dao.findByEmailAndPasswort("f@kiltz.de", "keins"))).thenReturn(kontoEntity);

        service = new KontoServiceImpl(dao, mock(Validator.class));
    }

    @Test
    void testNeuGoodDay() {
        Konto k = new Konto();
        k.setEmail("f@kiltz.de");
        k.setPasswort("Keins1&nix");
        assertNull(k.getId());
        try {
            Konto kNeu = service.neu(k);
            assertNotNull(kNeu.getId());
        } catch (SSOValidationException e) {
            fail(e);
        }

    }
    @Test
    void testNeuFehlendePflichtangaben() {
        Konto k = new Konto();
        try {
            Konto kNeu = service.neu(k);
            fail("Sollte eine Ex werfen!");
        } catch (SSOValidationException e) {
            // alles gut
        }
    }
    @Test
    void testNeuFehlendePflichtangabenInBesser() {
        Konto k = new Konto();
        assertThrows(SSOValidationException.class, () -> {
            service.neu(k);
        });
    }

    @Test
    void testNeuFehlendePflichtangabenAlternative() {
        Konto k = new Konto();
        assertThrows(SSOValidationException.class, () -> service.neu(k));
    }

    @Test
    void testFehlerhaftesLoginOhnePasswort() {
        assertThrows(SSOValidationException.class, () -> service.login("f@kiltz.de", ""));

    }
    @Test
    void testKorrektesLogin() {
        try {
            Konto k = service.login("f@kiltz.de", "keins");
            assertEquals("f@kiltz.de", k.getEmail());
            assertEquals("keins", k.getPasswort());
        } catch (SSOValidationException e) {
            fail("Sollte keine Ex werfen");
        }

    }
}
