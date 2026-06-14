package co.edu.udistrital.labarchivistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta tras un login exitoso.
 * El JWT NO se incluye en el body — se emite como cookie HttpOnly segura.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private UserResponse user;
    private String message;
}
