package co.edu.udistrital.labarchivistica.service;

import co.edu.udistrital.labarchivistica.dto.request.CreateUserRequest;
import co.edu.udistrital.labarchivistica.dto.request.UpdateUserRequest;
import co.edu.udistrital.labarchivistica.dto.response.UserResponse;
import co.edu.udistrital.labarchivistica.exception.DuplicateEmailException;
import co.edu.udistrital.labarchivistica.exception.ResourceNotFoundException;
import co.edu.udistrital.labarchivistica.model.UserEntity;
import co.edu.udistrital.labarchivistica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los usuarios del sistema.
 *
 * <p>Operaciones de escritura (crear, actualizar, desactivar) están restringidas
 * a ADMIN mediante {@code @PreAuthorize} en el controlador.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    // -------------------------------------------------------
    // Consultas
    // -------------------------------------------------------

    /**
     * Devuelve todos los usuarios registrados (solo ADMIN).
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Devuelve un usuario por su ID.
     *
     * @param id identificador del usuario
     * @return datos del usuario
     * @throws ResourceNotFoundException si el ID no existe
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return toResponse(findUserById(id));
    }

    /**
     * Devuelve el perfil del usuario autenticado en el contexto actual.
     *
     * @return datos del usuario autenticado
     */
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        String email = getAuthenticatedEmail();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
        return toResponse(user);
    }

    // -------------------------------------------------------
    // Escritura
    // -------------------------------------------------------

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param request datos del nuevo usuario
     * @return usuario creado
     * @throws DuplicateEmailException si el email ya existe
     */
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);

        UserEntity saved = userRepository.save(user);
        log.info("Usuario creado: {} (rol: {})", saved.getEmail(), saved.getRole());
        return toResponse(saved);
    }

    /**
     * Actualiza parcialmente un usuario existente.
     * Solo se actualizan los campos que no son {@code null} en el request.
     *
     * @param id      identificador del usuario
     * @param request campos a actualizar
     * @return usuario actualizado
     */
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        UserEntity user = findUserById(id);

        if (request.getName() != null)   user.setName(request.getName());
        if (request.getRole() != null)   user.setRole(request.getRole());
        if (request.getActive() != null) user.setActive(request.getActive());

        UserEntity saved = userRepository.save(user);
        log.info("Usuario actualizado: {}", saved.getEmail());
        return toResponse(saved);
    }

    /**
     * Desactiva una cuenta de usuario (soft delete).
     * El usuario permanece en base de datos pero no puede autenticarse.
     *
     * @param id identificador del usuario a desactivar
     */
    @Transactional
    public void deactivateUser(Long id) {
        UserEntity user = findUserById(id);
        user.setActive(false);
        userRepository.save(user);
        log.info("Usuario desactivado: {}", user.getEmail());
    }

    /**
     * Actualiza la fecha del último login del usuario.
     * Se llama desde {@link co.edu.udistrital.labarchivistica.controller.AuthController}
     * tras un login exitoso.
     *
     * @param email email del usuario que acaba de autenticarse
     */
    @Transactional
    public void updateLastLogin(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }

    // -------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------

    private UserEntity findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    private String getAuthenticatedEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // el principal es el email (username en UserDetails)
    }

    /**
     * Convierte una entidad usuario en su DTO de respuesta.
     */
    private UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .active(user.isActive())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
