package co.edu.udistrital.labarchivistica.controller;

import co.edu.udistrital.labarchivistica.dto.request.CreateApplicationRequest;
import co.edu.udistrital.labarchivistica.dto.request.UpdateApplicationRequest;
import co.edu.udistrital.labarchivistica.dto.response.ApiResponse;
import co.edu.udistrital.labarchivistica.dto.response.ApplicationResponse;
import co.edu.udistrital.labarchivistica.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el catálogo de aplicativos desplegados.
 *
 * <ul>
 *   <li>{@code GET /applications}      — Listado de todos los aplicativos (cualquier autenticado).</li>
 *   <li>{@code GET /applications/{id}}  — Obtener aplicativo por ID (cualquier autenticado).</li>
 *   <li>{@code POST /applications}     — Crear un nuevo aplicativo (ADMIN).</li>
 *   <li>{@code PUT /applications/{id}}  — Actualizar un aplicativo (ADMIN).</li>
 *   <li>{@code DELETE /applications/{id}} — Eliminar un aplicativo (ADMIN, hard delete).</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * Obtiene el listado completo de aplicativos.
     * Disponible para cualquier usuario autenticado (ADMIN, DOCENTE, ESTUDIANTE).
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> listAll() {
        List<ApplicationResponse> apps = applicationService.listAll();
        return ResponseEntity.ok(ApiResponse.ok("Catálogo de aplicativos obtenido", apps));
    }

    /**
     * Obtiene los detalles de un aplicativo específico por su ID.
     * Disponible para cualquier usuario autenticado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getById(@PathVariable Long id) {
        ApplicationResponse app = applicationService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Aplicativo obtenido correctamente", app));
    }

    /**
     * Registra un nuevo aplicativo en el catálogo.
     * Solo accesible por el rol ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ApplicationResponse>> create(@Valid @RequestBody CreateApplicationRequest request) {
        ApplicationResponse createdApp = applicationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Aplicativo registrado exitosamente", createdApp));
    }

    /**
     * Actualiza los datos de un aplicativo existente.
     * Solo accesible por el rol ADMIN.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ApplicationResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationRequest request
    ) {
        ApplicationResponse updatedApp = applicationService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Aplicativo actualizado correctamente", updatedApp));
    }

    /**
     * Elimina definitivamente un aplicativo del catálogo.
     * Solo accesible por el rol ADMIN.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Aplicativo eliminado correctamente"));
    }
}
