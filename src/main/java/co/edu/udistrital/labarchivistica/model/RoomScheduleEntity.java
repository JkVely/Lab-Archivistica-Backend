package co.edu.udistrital.labarchivistica.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "horarios_salas")
public class RoomScheduleEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private RoomEntity room;

    @Column(nullable = false)
    private Integer dayOfWeek; // 1 = Monday, 7 = Sunday

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
    
    private String observations;
}
