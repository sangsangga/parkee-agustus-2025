package parkingpos.ticket_service.model;

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

    @Column(name = "plate_number")
    @Getter
    @Setter
    private String plateNumber;

    @Column(name = "check_in_time")
    @Getter
    @Setter
    private ZonedDateTime checkInTime;

    @Column(name = "check_out_time")
    @Getter
    @Setter
    private ZonedDateTime checkOutTime;

    @Column
    @Getter
    @Setter
    private String status;
} 