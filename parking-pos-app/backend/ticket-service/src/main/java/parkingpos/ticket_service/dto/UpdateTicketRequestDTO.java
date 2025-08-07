package parkingpos.ticket_service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class UpdateTicketRequestDTO {
    
    private ZonedDateTime checkOutTime;
    
}
