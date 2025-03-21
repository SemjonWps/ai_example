package de.wps.dddschulung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;

@SpringBootApplication
@ImportAutoConfiguration(exclude = WebServiceTemplateAutoConfiguration.class)
public class WochenplanApplication {

    public static void main(String[] args) {
        SpringApplication.run(WochenplanApplication.class, args);
    }
}
