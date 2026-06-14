package co.edu.udistrital.labarchivistica.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Propiedades de configuración personalizadas del prefijo "app" en application.yml.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    /** Clave secreta para firmar los JWT (mínimo 32 caracteres en producción). */
    private String jwtSecret;

    /** Tiempo de expiración del JWT en milisegundos (default: 86400000 = 24 h). */
    private long jwtExpiration;

    /** Directorio donde se almacenan los documentos subidos. */
    private String uploadDir;
}
