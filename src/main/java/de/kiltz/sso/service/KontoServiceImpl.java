package de.kiltz.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.kiltz.sso.dao.KontoDao;
import de.kiltz.sso.data.KontoEntity;
import de.kiltz.sso.model.Konto;
import de.kiltz.sso.model.converter.KontoConverter;

/**
 * @author tz
 */
@Service
public class KontoServiceImpl implements KontoService{
    
    private final KontoDao dao;

    @Autowired
    public KontoServiceImpl(KontoDao dao) {
        this.dao = dao;
    }

    @Override
    public Konto neu(Konto k) throws SSOValidationException {
        validiere(k);
        KontoEntity e = dao.save(KontoConverter.kontoEntity(k));
        return KontoConverter.toModel(e);
    }

    private void validiere(Konto k)  throws SSOValidationException  {
        StringBuilder fehler = new StringBuilder();
        if (k.getEmail() == null || k.getEmail().isEmpty()){
            fehler.append("Email ist ein Pflichtfeld");
        }
        // ...
        if (fehler.length() > 0) {
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
        KontoEntity e = dao.findByEmail(email);
        return e == null ? null : KontoConverter.toModel(e);
    }

    @Override
    public List<Konto> suche(String suchbegriff) {
        List<Konto> liste = KontoConverter.toModel(dao.findByNachnameContains(suchbegriff));
        return liste;
    }
}
