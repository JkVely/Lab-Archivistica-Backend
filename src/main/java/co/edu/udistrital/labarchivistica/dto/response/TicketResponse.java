package co.edu.udistrital.labarchivistica.dto.response;

import java.time.LocalDateTime;

import co.edu.udistrital.labarchivistica.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private Long userId;
    private String subject;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;
}
