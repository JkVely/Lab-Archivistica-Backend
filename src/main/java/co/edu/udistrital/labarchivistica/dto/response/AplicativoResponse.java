package co.edu.udistrital.labarchivistica.dto.response;

import java.time.LocalDateTime;

import co.edu.udistrital.labarchivistica.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AplicativoResponse {
    private Long id;
    private String name;
    private String version;
    private String descripcion;
    private String urlAcceso;
    private ResourceStatus status;
    private LocalDateTime createdAt;
}
