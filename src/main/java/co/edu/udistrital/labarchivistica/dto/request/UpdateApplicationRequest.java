package co.edu.udistrital.labarchivistica.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de solicitud para actualizar un aplicativo existente (solo ADMIN).
 * Todos los campos son opcionales: solo se actualizan los que no son {@code null}.
 */
@Data
public class UpdateApplicationRequest {

    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String name;

    @Size(max = 50, message = "La versión no puede superar 50 caracteres")
    private String appVersion;

    private String description;

    @Size(max = 100, message = "La categoría no puede superar 100 caracteres")
    private String category;

    @Size(max = 500, message = "La URL de acceso no puede superar 500 caracteres")
    private String accessUrl;

    @Size(max = 500, message = "La URL del ícono no puede superar 500 caracteres")
    private String iconUrl;
}
