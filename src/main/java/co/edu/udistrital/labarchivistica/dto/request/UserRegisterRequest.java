package co.edu.udistrital.labarchivistica.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @Email
    @NotBlank
    @Pattern(regexp = ".+@udistrital\\.edu\\.co", message = "El correo debe ser @udistrital.edu.co")
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}
