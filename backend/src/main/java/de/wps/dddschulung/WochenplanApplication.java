package de.wps.dddschulung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ImportAutoConfiguration(exclude = WebServiceTemplateAutoConfiguration.class)
public class WochenplanApplication {

	public static void main(String[] args) {
		SpringApplication.run(WochenplanApplication.class, args);
	}
}
