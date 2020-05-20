package de.kiltz.sso;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.kiltz.sso.service.KontoService;

@SpringBootTest
class SsoApplicationTests {

	@Autowired
	private KontoService service;
	@Test
	void contextLoads() {
		assertNotNull(service);
	}

}
