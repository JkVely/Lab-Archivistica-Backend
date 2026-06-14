package co.edu.udistrital.labarchivistica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se intenta registrar un email que ya existe en el sistema.
 * Genera automáticamente una respuesta HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Ya existe un usuario con el email: " + email);
    }
}
