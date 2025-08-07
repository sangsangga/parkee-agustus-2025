package parkingpos.checkinservice.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class CreateTicketResponseDTO {
    Long id;
    String plateNumber;
    ZonedDateTime checkInTime;
    ZonedDateTime checkOutTime;
    String status;
}
