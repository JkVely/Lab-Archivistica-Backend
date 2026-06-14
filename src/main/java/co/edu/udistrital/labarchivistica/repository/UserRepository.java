package co.edu.udistrital.labarchivistica.repository;

import co.edu.udistrital.labarchivistica.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link UserEntity}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email dirección de correo
     * @return {@link Optional} con el usuario si existe
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Verifica si ya existe un usuario con el email dado.
     *
     * @param email dirección de correo
     * @return {@code true} si el email ya está registrado
     */
    boolean existsByEmail(String email);
}
