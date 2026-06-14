package co.edu.udistrital.labarchivistica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomScheduleResponse {
    private Long id;
    private Long roomId;
    private String roomName;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
