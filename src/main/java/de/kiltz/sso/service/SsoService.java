package de.kiltz.sso.service;

/**
 * @author tz
 */
public interface SsoService {

    String createToken(String email);
    String getToken(String email);
    boolean validate(String email, String token);
    void delete(String email, String token);
}
