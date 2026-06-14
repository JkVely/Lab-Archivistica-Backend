package co.edu.udistrital.labarchivistica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "software")
public class SoftwareEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String softwareVersion;

    private String license;

    @Column(columnDefinition = "text")
    private String description;

    private String documentationUrl;
}
