package de.kiltz.sso.rest.v1;

import de.kiltz.sso.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.kiltz.sso.model.Konto;
import de.kiltz.sso.service.KontoService;
import de.kiltz.sso.service.SSOValidationException;
import de.kiltz.sso.service.SsoService;

/**
 * @author tz
 */

@RestController
@RequestMapping(path = "rs/konto")
public class KontoRestService {

    private final KontoService service;
    private final SsoService ssoService;

    @Autowired
    public KontoRestService(KontoService service, SsoService ssoService) {
        this.service = service;
        this.ssoService = ssoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Konto> get(@RequestParam("email") String email, @RequestParam("token") String token) {
        if (!ssoService.validate(email, token)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(service.holePerEmail(email), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> neu(@RequestBody Konto k) throws SSOValidationException {
        Konto kNeu = service.neu(k);
        return new ResponseEntity<>(kNeu.getId(), HttpStatus.CREATED);
    }

    @ExceptionHandler(SSOValidationException.class)
    public ResponseEntity<String> exceptionHandler(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePasswort(@RequestParam("email") String email,
                                         @RequestParam("altesPasswort") String altesPasswort,
                                         @RequestParam("neuesPasswort") String neuesPasswort) throws SSOValidationException {
        if (!TextUtils.pruefePasswort(neuesPasswort)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Konto k = service.login(email, altesPasswort);
        if (k == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        k.setPasswort(neuesPasswort);
        service.aktualisiere(k);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
