package de.kiltz.sso.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.kiltz.sso.model.Konto;
import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SSOValidationException;

/**
 * @author tz
 */

@RestController
@RequestMapping(path="rs/konto")
public class KontoRestService {

    private final KontoService service;

    @Autowired
    public KontoRestService(KontoService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam("email") String email) {
        Konto k = service.holePerEmail(email);

        return k == null ? "Nicht gefunden" : k.toString();
    }

    @RequestMapping(method = RequestMethod.GET, path="test")
    public void teste() throws SSOValidationException {
        Konto k = new Konto();
        k.setVorname("Friedrich");
        k.setNachname("Kiltz");
        k.setEmail("f@kiltz.de");
        k.setPasswort("keins");
        k = service.neu(k);

        System.out.println(k);

    }

}
