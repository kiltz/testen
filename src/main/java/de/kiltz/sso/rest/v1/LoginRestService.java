package de.kiltz.sso.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SsoService;

/**
 * @author tz
 */
@RestController
@RequestMapping(path="rs/login")
public class LoginRestService {
    private final KontoService service;
    private final SsoService ssoService;

    @Autowired
    public LoginRestService(KontoService service, SsoService ssoService) {
        this.service = service;
        this.ssoService = ssoService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@RequestParam("email") String email, @RequestParam("passwort") String passwort){
        System.out.println("Login mit "+email);
        if (service.login(email, passwort) != null) {
            return ssoService.createToken(email);
        }
        return null;
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void logout(String email, String token) {
        ssoService.delete(email, token);
    }
}
