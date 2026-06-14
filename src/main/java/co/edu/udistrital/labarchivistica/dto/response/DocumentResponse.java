package co.edu.udistrital.labarchivistica.dto.response;

import co.edu.udistrital.labarchivistica.model.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private Long id;
    private String title;
    private DocumentType type;
    private String filePath;
    private String mimeType;
    private Long sizeBytes;
    private Long uploadedById;
    private String uploadedByEmail;
    private LocalDateTime createdAt;
}
