package de.kiltz.sso.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SsoServiceTest {

    public final static String DEFAULT_EMAIL = "postfach@tester.de";

    @Autowired
    SsoService ssoService;

    @Test
    void IsLoggedTest() {
        String token = ssoService.createToken(DEFAULT_EMAIL);

    }


}
