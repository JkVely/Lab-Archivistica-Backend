package co.edu.udistrital.labarchivistica.dto.response;

import co.edu.udistrital.labarchivistica.model.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private Long roomId;
    private String roomName;
    private Long userId;
    private String userEmail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
    private String purpose;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
