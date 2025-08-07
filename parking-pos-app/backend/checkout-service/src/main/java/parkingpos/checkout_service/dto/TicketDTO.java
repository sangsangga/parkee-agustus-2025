package parkingpos.checkout_service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class TicketDTO {
    
    private Long id;
    private String plateNumber;
    private ZonedDateTime checkInTime;
    private ZonedDateTime checkOutTime;
    private String status;
    
}
