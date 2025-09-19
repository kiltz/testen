package de.kiltz.sso.rest.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
class LoginRestServiceTest {

    @Autowired
    LoginRestService loginRestService;

    @Test
    void loginTest() {

        try {
            var entity = loginRestService.login("test2@testa.de", "keins");
            var entity2 = loginRestService.login("test2@testa.de", "keins");
            assertEquals(entity.getBody(), entity2.getBody());
        }
        catch (Exception e) {
            fail("Hier sollte keine Ex werfen werden");
            e.printStackTrace();
        }
    }

}
