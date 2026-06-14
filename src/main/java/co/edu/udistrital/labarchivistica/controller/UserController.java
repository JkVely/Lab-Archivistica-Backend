package co.edu.udistrital.labarchivistica.controller;

import co.edu.udistrital.labarchivistica.dto.request.CreateUserRequest;
import co.edu.udistrital.labarchivistica.dto.request.UpdateUserRequest;
import co.edu.udistrital.labarchivistica.dto.response.ApiResponse;
import co.edu.udistrital.labarchivistica.dto.response.UserResponse;
import co.edu.udistrital.labarchivistica.model.Role;
import co.edu.udistrital.labarchivistica.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 *
 * <ul>
 *   <li>{@code GET /users}      — Listado de todos los usuarios (ADMIN).</li>
 *   <li>{@code GET /users/{id}}  — Obtener perfil de usuario (ADMIN o el propio usuario).</li>
 *   <li>{@code POST /users}     — Crear un nuevo usuario (ADMIN).</li>
 *   <li>{@code PUT /users/{id}}  — Actualizar un usuario (ADMIN).</li>
 *   <li>{@code DELETE /users/{id}} — Desactivar un usuario (ADMIN, soft delete).</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Devuelve el listado de todos los usuarios.
     * Solo accesible por el rol ADMIN.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ok("Listado de usuarios obtenido", users));
    }

    /**
     * Devuelve un usuario por su ID.
     * Accesible por el rol ADMIN, o por cualquier usuario autenticado siempre que sea su propio perfil.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse currentUser = userService.getCurrentUser();
        
        // Un usuario que no sea ADMIN solo puede ver su propia información
        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(id)) {
            log.warn("Intento de acceso no autorizado por {} al perfil {}", currentUser.getEmail(), id);
            throw new AccessDeniedException("No tiene permisos para ver este perfil");
        }
        
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario obtenido correctamente", user));
    }

    /**
     * Crea un nuevo usuario.
     * Solo accesible por el rol ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Usuario creado exitosamente", createdUser));
    }

    /**
     * Actualiza los datos de un usuario.
     * Solo accesible por el rol ADMIN.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Usuario actualizado correctamente", updatedUser));
    }

    /**
     * Desactiva un usuario (soft delete).
     * Solo accesible por el rol ADMIN.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario desactivado correctamente"));
    }
}
