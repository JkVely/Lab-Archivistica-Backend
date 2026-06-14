package co.edu.udistrital.labarchivistica.service;

import co.edu.udistrital.labarchivistica.dto.request.CreateApplicationRequest;
import co.edu.udistrital.labarchivistica.dto.request.UpdateApplicationRequest;
import co.edu.udistrital.labarchivistica.dto.response.ApplicationResponse;
import co.edu.udistrital.labarchivistica.exception.ResourceNotFoundException;
import co.edu.udistrital.labarchivistica.model.ApplicationEntity;
import co.edu.udistrital.labarchivistica.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que gestiona el catálogo de aplicativos desplegados en el servidor.
 *
 * <p>Listado y consulta están disponibles para cualquier usuario autenticado.
 * Crear, actualizar y eliminar están restringidos a ADMIN mediante
 * {@code @PreAuthorize} en el controlador.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    // -------------------------------------------------------
    // Consultas
    // -------------------------------------------------------

    /**
     * Devuelve el listado completo de aplicativos del catálogo.
     *
     * @return lista de todos los aplicativos
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> listAll() {
        return applicationRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Devuelve un aplicativo por su ID.
     *
     * @param id identificador del aplicativo
     * @return datos del aplicativo
     * @throws ResourceNotFoundException si el ID no existe
     */
    @Transactional(readOnly = true)
    public ApplicationResponse getById(Long id) {
        return toResponse(findApplicationById(id));
    }

    // -------------------------------------------------------
    // Escritura (solo ADMIN)
    // -------------------------------------------------------

    /**
     * Crea un nuevo aplicativo en el catálogo.
     *
     * @param request datos del nuevo aplicativo
     * @return aplicativo creado
     */
    @Transactional
    public ApplicationResponse create(CreateApplicationRequest request) {
        ApplicationEntity app = new ApplicationEntity();
        app.setName(request.getName());
        app.setAppVersion(request.getAppVersion());
        app.setDescription(request.getDescription());
        app.setCategory(request.getCategory());
        app.setAccessUrl(request.getAccessUrl());
        app.setIconUrl(request.getIconUrl());

        ApplicationEntity saved = applicationRepository.save(app);
        log.info("Aplicativo creado: {} v{}", saved.getName(), saved.getAppVersion());
        return toResponse(saved);
    }

    /**
     * Actualiza parcialmente un aplicativo existente.
     * Solo se actualizan los campos que no son {@code null} en el request.
     *
     * @param id      identificador del aplicativo
     * @param request campos a actualizar
     * @return aplicativo actualizado
     */
    @Transactional
    public ApplicationResponse update(Long id, UpdateApplicationRequest request) {
        ApplicationEntity app = findApplicationById(id);

        if (request.getName()       != null) app.setName(request.getName());
        if (request.getAppVersion() != null) app.setAppVersion(request.getAppVersion());
        if (request.getDescription()!= null) app.setDescription(request.getDescription());
        if (request.getCategory()   != null) app.setCategory(request.getCategory());
        if (request.getAccessUrl()  != null) app.setAccessUrl(request.getAccessUrl());
        if (request.getIconUrl()    != null) app.setIconUrl(request.getIconUrl());

        ApplicationEntity saved = applicationRepository.save(app);
        log.info("Aplicativo actualizado: {}", saved.getName());
        return toResponse(saved);
    }

    /**
     * Elimina definitivamente un aplicativo del catálogo.
     *
     * @param id identificador del aplicativo
     */
    @Transactional
    public void delete(Long id) {
        ApplicationEntity app = findApplicationById(id);
        applicationRepository.delete(app);
        log.info("Aplicativo eliminado: {}", app.getName());
    }

    // -------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------

    private ApplicationEntity findApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aplicativo", "id", id));
    }

    /**
     * Convierte una entidad aplicativo en su DTO de respuesta.
     */
    private ApplicationResponse toResponse(ApplicationEntity app) {
        return ApplicationResponse.builder()
                .id(app.getId())
                .name(app.getName())
                .appVersion(app.getAppVersion())
                .description(app.getDescription())
                .accessUrl(app.getAccessUrl())
                .category(app.getCategory())
                .iconUrl(app.getIconUrl())
                .createdAt(app.getCreatedAt())
                .updatedAt(app.getUpdatedAt())
                .build();
    }
}
