package co.edu.udistrital.labarchivistica.dto.request;

import co.edu.udistrital.labarchivistica.model.Role;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de solicitud para actualizar un usuario existente (solo ADMIN).
 * Todos los campos son opcionales: solo se actualizan los que no son {@code null}.
 */
@Data
public class UpdateUserRequest {

    @Size(max = 255, message = "El nombre no puede superar 255 caracteres")
    private String name;

    private Role role;

    private Boolean active;
}
