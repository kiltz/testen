package de.kiltz.sso.rest.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import de.kiltz.sso.model.Konto;
import de.kiltz.sso.rest.RestService;
import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SSOValidationException;

/**
 * @author tz
 */

@Path("/sso/konto")
@RestService
public class KontoRestService {

    private final KontoService service;

    @Autowired
    public KontoRestService(KontoService service) {
        this.service = service;
    }

    @GET
    public String get(@QueryParam("email") String email) {
        Konto k = service.holePerEmail(email);

        return k == null ? "Nicht gefunden" : k.toString();
    }

    @Path("test")
    @GET
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
