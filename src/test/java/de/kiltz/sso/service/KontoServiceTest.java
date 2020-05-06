package de.kiltz.sso.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.data.KontoEntity;
import de.kiltz.sso.model.Konto;

/**
 * @author tz
 */
@SpringBootTest
public class KontoServiceTest {

    private KontoService service;

    @BeforeEach
    void init() {
        KontoDao dao = mock(KontoDao.class);
        when(dao.save(any(KontoEntity.class))).then(i -> {
            KontoEntity k = i.getArgument(0, KontoEntity.class);
            k.setId(1L);
            return k;
        });
        service = new KontoServiceImpl(dao);
    }

    @Test
    void testNeuGoodDay() {
        Konto k = new Konto();
        k.setEmail("f@kiltz.de");
        assertNull(k.getId());
        try {
            Konto kNeu = service.neu(k);
            assertNotNull(kNeu.getId());
        } catch (SSOValidationException e) {
            fail(e);
        }

    }
}
