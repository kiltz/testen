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
    public String getOrCreateToken(String email) {
//        if (!tokenMap.containsKey(email)) {
//            tokenMap.put(email, UUID.randomUUID().toString());
//        }
//        return tokenMap.get(email);
        // in neu und kurz:
        return tokenMap.computeIfAbsent(email, key -> UUID.randomUUID().toString());
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
