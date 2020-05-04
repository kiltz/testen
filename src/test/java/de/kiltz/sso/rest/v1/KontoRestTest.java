package de.kiltz.sso.rest.v1;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.jayway.jsonpath.spi.json.JacksonJsonProvider;

/**
 * @author tz
 */
public class KontoRestTest {
    private static final String URL = "http://localhost:8080/sso/konto/";
    private static Client client;

    @BeforeAll
    static void init() {
        client = ClientBuilder.newClient().register(new JacksonJsonProvider());

    }

    @AfterAll
    static void beende() {
        client.close();
    }

//@Test
    public void testPing() {
        String query = "f@kiltz.de";
        WebTarget target = client.target(URL).queryParam("email", query);
        String resp = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
    System.out.println(resp);

    }

}
