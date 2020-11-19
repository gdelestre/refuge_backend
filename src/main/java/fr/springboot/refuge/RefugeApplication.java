package fr.springboot.refuge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Pour le déploiement il est necessaire que notre classe hérite de SpringBootServletInitializer. Rajout de la méthode protected.
@SpringBootApplication
public class RefugeApplication extends SpringBootServletInitializer {
	public RefugeApplication() {
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(new Class[]{RefugeApplication.class});
	}

	public static void main(String[] args) {
		SpringApplication.run(RefugeApplication.class, args);
	}
}
