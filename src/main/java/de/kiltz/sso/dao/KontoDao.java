package de.kiltz.sso.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kiltz.sso.data.KontoEntity;

/**
 * @author tz
 */
@Repository
public interface KontoDao extends JpaRepository<KontoEntity, Long> {

    KontoEntity findByEmailIgnoreCaseAndPasswort(String email, String passwort);
    KontoEntity findByEmailIgnoreCase(String email);

    List<KontoEntity> findByNachnameContainsIgnoreCase(String suchbegriff);
}
