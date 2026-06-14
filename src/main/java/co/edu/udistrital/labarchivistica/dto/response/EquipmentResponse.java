package co.edu.udistrital.labarchivistica.dto.response;

import co.edu.udistrital.labarchivistica.model.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private EquipmentStatus status;
    private String imageUrl;
    private String brand;
    private String model;
    private String supplier;
    private Long roomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
