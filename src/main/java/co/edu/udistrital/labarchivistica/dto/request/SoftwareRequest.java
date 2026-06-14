package co.edu.udistrital.labarchivistica.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String softwareVersion;

    @Size(max = 100)
    private String license;

    @Size(max = 500)
    private String description;

    private String documentationUrl;
}
