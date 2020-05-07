package de.kiltz.sso.service;

import java.util.List;

import de.kiltz.sso.model.Konto;

/**
 * @author tz
 */
public interface KontoService {

    Konto neu(Konto k) throws SSOValidationException;
    void loesche(Konto k);
    Konto aktualisiere(Konto k) throws SSOValidationException;
    Konto holePerEmail(String email);
    Konto login(String email, String passwort);

    List<Konto> suche(String suchbegriff);
}
