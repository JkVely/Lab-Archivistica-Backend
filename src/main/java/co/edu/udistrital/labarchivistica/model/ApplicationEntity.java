package co.edu.udistrital.labarchivistica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "aplicativos")
public class ApplicationEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String appVersion;

    @Column(columnDefinition = "text")
    private String description;

    @Column(length = 500)
    private String accessUrl;

    @Column(length = 100)
    private String category;

    @Column(length = 500)
    private String iconUrl;
}
