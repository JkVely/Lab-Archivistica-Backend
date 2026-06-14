package co.edu.udistrital.labarchivistica.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    private Integer capacity;

    @Size(max = 100)
    private String location;
}
