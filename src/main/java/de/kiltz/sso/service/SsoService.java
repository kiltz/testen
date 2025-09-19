package de.kiltz.sso.service;

/**
 * @author tz
 */
public interface SsoService {

    boolean validate(String email, String token);
    void delete(String email, String token);

    String getToken(String email);
}
