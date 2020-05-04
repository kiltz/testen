package de.kiltz.sso.rest;

import java.util.List;
import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * @author tz
 */
@Component
public class JerseyConfig extends ResourceConfig {

    private static final Logger LOG = Logger.getLogger(JerseyConfig.class.getName());

    public JerseyConfig(@RestService List<Object> restServices) {
        restServices.forEach(e -> {
            LOG.info("register RestService: "+e.getClass().getName());
            register(e.getClass());
        });

    }
}