package parkingpos.checkinservice.model;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private String plateNumber;

    @Column
    @Getter
    @Setter
    private ZonedDateTime checkInTime;

    @Column
    @Getter
    @Setter
    private ZonedDateTime checkOutTime;

    @Column
    @Getter
    @Setter
    private String status;
    
}
