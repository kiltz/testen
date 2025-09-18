package de.kiltz.sso.service;

import java.util.List;

import de.kiltz.sso.utils.TextUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.data.KontoEntity;
import de.kiltz.sso.model.Konto;
import de.kiltz.sso.model.converter.KontoConverter;
import org.springframework.validation.*;

/**
 * @author tz
 */
@Service
public class KontoServiceImpl implements KontoService{
    
    private final KontoDao dao;
    private final Validator validator;

    @Autowired
    public KontoServiceImpl(KontoDao dao, Validator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    @Override
    public Konto neu(Konto k) throws SSOValidationException {
        k.setEmail(k.getEmail().trim());
        validiere(k);
        if (dao.findByEmailIgnoreCase(k.getEmail()) != null) {
            throw new SSOValidationException("Die E-Mail-Adresse ist schon vergeben!");
        }
        KontoEntity e = dao.save(KontoConverter.kontoEntity(k));
        return KontoConverter.toModel(e);
    }

    private void validiere(Konto k)  throws SSOValidationException  {
        Errors errors = new BeanPropertyBindingResult(k, k.getClass().getSimpleName());
        validator.validate(k, errors);
        if (errors.hasErrors()) {
            throw new SSOValidationException("Validierung fehlgeschlagen: "+errors);
        }
        if (!TextUtils.pruefePasswort(k.getPasswort())) {
            throw new SSOValidationException("Validierung fehlgeschlagen: " +
                    "Passwort entspricht nicht den Regeln");
        }
        StringBuilder fehler = new StringBuilder();
        if (k.getEmail() == null || k.getEmail().isEmpty()){
            fehler.append("Email ist ein Pflichtfeld");
        }
        // ...
        if (!fehler.isEmpty()) {
            throw new SSOValidationException(fehler.toString());
        }
    }

    @Override
    public void loesche(Konto k) {
        dao.deleteById(k.getId());
    }

    @Override
    public Konto aktualisiere(Konto k) throws SSOValidationException {
        validiere(k);
        KontoEntity e = dao.save(KontoConverter.kontoEntity(k));
        return KontoConverter.toModel(e);
    }

    @Override
    public Konto holePerEmail(String email) {
        KontoEntity e = dao.findByEmailIgnoreCase(email);
        return e == null ? null : KontoConverter.toModel(e);
    }

    @Override
    public Konto login(String email, String passwort) throws SSOValidationException {
        if (Strings.isEmpty(email) || Strings.isEmpty(passwort)) {
            throw new SSOValidationException("Validierung fehlgeschlagen: Email und Passwort dürfen nicht leer sein.");
        }

        KontoEntity e = dao.findByEmailIgnoreCaseAndPasswort(email, passwort);
        return e == null ? null : KontoConverter.toModel(e);
    }

    @Override
    public List<Konto> suche(String suchbegriff) {
        List<Konto> liste = KontoConverter.toModel(dao.findByNachnameContainsIgnoreCase(suchbegriff));
        return liste;
    }
}
