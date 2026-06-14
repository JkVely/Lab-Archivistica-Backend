package co.edu.udistrital.labarchivistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO de respuesta con los datos de un aplicativo del catálogo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private String name;
    private String appVersion;
    private String description;
    private String accessUrl;
    private String category;
    private String iconUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
