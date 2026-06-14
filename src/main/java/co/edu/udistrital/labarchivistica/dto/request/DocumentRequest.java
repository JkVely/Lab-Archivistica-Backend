package co.edu.udistrital.labarchivistica.dto.request;

import co.edu.udistrital.labarchivistica.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotNull
    private DocumentType type;

    // El archivo físico se manejará en el controlador como MultipartFile
}
