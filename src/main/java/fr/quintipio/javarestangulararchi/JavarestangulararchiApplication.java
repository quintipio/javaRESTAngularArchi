package fr.quintipio.javarestangulararchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Démarrage de l'application
 */
@SpringBootApplication
@EnableScheduling
public class JavarestangulararchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavarestangulararchiApplication.class, args);
	}
}
