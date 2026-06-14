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
@Table(name = "reservas_salas")
public class ReservationEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(columnDefinition = "text")
    private String purpose;
}
