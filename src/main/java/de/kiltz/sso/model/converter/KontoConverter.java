package de.kiltz.sso.model.converter;

import java.util.ArrayList;
import java.util.List;

import de.kiltz.sso.data.KontoEntity;
import de.kiltz.sso.model.Konto;
/**
 * Könnte z.B. durch Spring-Converter oder Mapstruct ersetzt werden.
 * @author tz
 */
public abstract class KontoConverter {

    public static Konto toModel(KontoEntity k) {
        Konto ret = new Konto();
        ret.setId(k.getId());
        ret.setVorname(k.getVorname());
        ret.setNachname(k.getNachname());
        ret.setEmail(k.getEmail());
        ret.setPasswort(k.getPasswort());
    return ret;
    }
    public static KontoEntity kontoEntity(Konto k) {
        KontoEntity ret = new KontoEntity();
        ret.setId(k.getId());
        ret.setVorname(k.getVorname());
        ret.setNachname(k.getNachname());
        ret.setEmail(k.getEmail());
        ret.setPasswort(k.getPasswort());
        return ret;
    }

    public static List<Konto> toModel(List<KontoEntity> liste) {
        List<Konto> ret = new ArrayList<>();
        liste.forEach(e -> ret.add(toModel(e)));
        return ret;
    }
}
