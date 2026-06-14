package co.edu.udistrital.labarchivistica.dto.request;

import co.edu.udistrital.labarchivistica.model.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 100)
    private String category;

    @NotNull
    private EquipmentStatus status;

    private String imageUrl;

    @Size(max = 100)
    private String brand;

    @Size(max = 100)
    private String model;

    @Size(max = 100)
    private String supplier;

    private Long roomId;
}
