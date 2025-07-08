package de.kiltz.sso;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoApplication {
	public static void main(String[] args)  {
		SpringApplication.run(SsoApplication.class, args);
	}
}
