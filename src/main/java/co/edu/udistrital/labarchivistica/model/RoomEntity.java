package co.edu.udistrital.labarchivistica.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "salas")
public class RoomEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    private Integer capacity;

    @Column(length = 100)
    private String location;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EquipmentEntity> equipments = new ArrayList<>();
}
