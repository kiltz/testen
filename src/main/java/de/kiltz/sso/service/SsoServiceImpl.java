package de.kiltz.sso.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;


/**
 * @author tz
 */
@Service
public class SsoServiceImpl implements SsoService{
    private final Map<String, String> tokenMap = new HashMap<>();

    @Override
    public String createToken(String email) {
        if (tokenMap.containsKey(email)) {
            return tokenMap.get(email);
        }
        String token = UUID.randomUUID().toString();
        tokenMap.put(email, token);
        return token;
    }

    @Override
    public boolean validate(String email, String token) {
//        tokenMap.forEach((k, v) -> System.out.println(k+": "+v));
        return tokenMap.get(email) != null && tokenMap.get(email).equals(token);
    }

    @Override
    public void delete(String email, String token) {
        tokenMap.remove(email);
    }
}
