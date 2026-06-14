package co.edu.udistrital.labarchivistica.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "co.edu.udistrital.labarchivistica")
public class LabArchivisticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabArchivisticaApplication.class, args);
    }
}