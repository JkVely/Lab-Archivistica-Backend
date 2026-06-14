package co.edu.udistrital.labarchivistica.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "documentos")
public class DocumentEntity extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Column(nullable = false)
    private String filePath;

    private String mimeType;

    private Long sizeBytes;

    @ManyToOne
    @JoinColumn(name = "uploaded_by_id")
    private UserEntity uploadedBy;

}
