package parkingpos.ticket_service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class TicketResponseDTO {
    
    private Long id;
    private String plateNumber;
    private ZonedDateTime checkInTime;
    private ZonedDateTime checkOutTime;
    private String status;
    
}
