package de.kiltz.sso.rest.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(path = "rs/konto")
public class KontoRestService {

    private final KontoService service;
    private final SsoService ssoService;

    public KontoRestService(KontoService service, SsoService ssoService) {
        this.service = service;
        this.ssoService = ssoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Konto> get(
            @RequestParam("email") String email,
            @RequestParam("token") String token) {

        if (!ssoService.validate(email, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Konto konto = service.holePerEmail(email);
        return ResponseEntity.ok(konto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> neu(@RequestBody Konto konto) throws SSOValidationException {
        Konto neuesKonto = service.neu(konto);
        return ResponseEntity.status(HttpStatus.CREATED).body(neuesKonto.getId());
    }

    @ExceptionHandler(SSOValidationException.class)
    public ResponseEntity<String> handleSSOValidationException(SSOValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}