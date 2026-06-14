package co.edu.udistrital.labarchivistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareResponse {
    private Long id;
    private String name;
    private String softwareVersion;
    private String license;
    private String description;
    private String documentationUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
