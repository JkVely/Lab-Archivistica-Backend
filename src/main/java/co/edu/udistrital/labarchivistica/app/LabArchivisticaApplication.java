package co.edu.udistrital.labarchivistica.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "co.edu.udistrital.labarchivistica")
@EnableJpaRepositories(basePackages = "co.edu.udistrital.labarchivistica.repository")
@EntityScan(basePackages = "co.edu.udistrital.labarchivistica.model")
public class LabArchivisticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabArchivisticaApplication.class, args);
    }
}