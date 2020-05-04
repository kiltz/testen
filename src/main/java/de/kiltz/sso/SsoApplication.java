package de.kiltz.sso;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoApplication {
	private static final Logger LOG = Logger.getLogger(SsoApplication.class.getName());
	public static void main(String[] args) throws IOException {
		SpringApplication.run(SsoApplication.class, args);
	}
}
