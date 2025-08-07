package parkingpos.ticket_service.dto.Messaging;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreatedMessageDTO {
    private Long ticketId;
    private String plateNumber;
    private ZonedDateTime checkInTime;
    private String status;
    private String correlationId;
}
