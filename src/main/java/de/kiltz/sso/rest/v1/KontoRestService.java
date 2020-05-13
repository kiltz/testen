package de.kiltz.sso.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.kiltz.sso.model.Konto;
import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SSOValidationException;
import de.kiltz.sso.service.SsoService;

/**
 * @author tz
 */

@RestController
@RequestMapping(path="rs/konto")
public class KontoRestService {

    private final KontoService service;
    private final SsoService ssoService;

    @Autowired
    public KontoRestService(KontoService service, SsoService ssoService) {
        this.service = service;
        this.ssoService = ssoService;
    }

    @GetMapping
    public String get(@RequestParam("email") String email, @RequestParam("token") String token ) throws SSOValidationException {
        if (!ssoService.validate(email, token)) {
            return "Du bist für diese Funktion nicht berechtigt!";
        }

        Konto k = service.holePerEmail(email);

        return k == null ? "Nicht gefunden" : k.toString();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void neu(Konto k) throws SSOValidationException {
        System.out.println(k);
        k = service.neu(k);


    }

}
