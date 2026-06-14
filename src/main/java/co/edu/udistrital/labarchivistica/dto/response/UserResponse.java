package co.edu.udistrital.labarchivistica.dto.response;

import java.time.LocalDateTime;

import co.edu.udistrital.labarchivistica.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta con el perfil completo de un usuario.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private Role role;
    private boolean active;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
