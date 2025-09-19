package de.kiltz.sso.service;

import de.kiltz.sso.rest.v1.LoginRestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/dataCrud.sql")
@Transactional
class LoginServiceTest {

    private static final String DEFAULT_MAIL = "test2@testa.de";
    private static final String DEFAULT_PASSWORD = "keins";

    @Autowired
    private SsoService ssoService;
    @Autowired
    private KontoService kontoService;
    @Autowired
    private LoginRestService loginRestService;

    @Test
    void testWiederholtesLogin() throws SSOValidationException {
        var token1 = loginRestService.login(DEFAULT_MAIL, DEFAULT_PASSWORD);
        var token2 = loginRestService.login(DEFAULT_MAIL, DEFAULT_PASSWORD);

        assertEquals(token1, token2);
    }

    @Test
    void testTokenVerfallNachLogout() throws SSOValidationException {

        var token1 = loginRestService.login(DEFAULT_MAIL, DEFAULT_PASSWORD).toString().split(",")[1];

        assertTrue(ssoService.validate(DEFAULT_MAIL, token1));

        loginRestService.logout(DEFAULT_MAIL, token1);

        assertFalse(ssoService.validate(DEFAULT_MAIL, token1));
    }
}
