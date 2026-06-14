package co.edu.udistrital.labarchivistica.repository;

import co.edu.udistrital.labarchivistica.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link ApplicationEntity}.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    /**
     * Filtra aplicativos por categoría.
     *
     * @param category nombre de la categoría
     * @return lista de aplicativos de esa categoría
     */
    List<ApplicationEntity> findByCategory(String category);

    /**
     * Verifica si ya existe un aplicativo con el mismo nombre.
     *
     * @param name nombre del aplicativo
     * @return {@code true} si el nombre ya existe
     */
    boolean existsByName(String name);
}
