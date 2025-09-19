package de.kiltz.sso.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SsoServiceTest {

    public static final String DEFAULT_EMAIL = "postfach@tester.de";

    @Autowired
    SsoService ssoService;

    @Test
    void IsLoggedTest() {
        String token = ssoService.getToken(DEFAULT_EMAIL);
        assertEquals(ssoService.getToken(DEFAULT_EMAIL), token);
    }
}
