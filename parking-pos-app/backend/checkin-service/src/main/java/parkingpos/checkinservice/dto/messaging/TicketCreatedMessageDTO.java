package parkingpos.checkinservice.dto.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreatedMessageDTO {
    private Long ticketId;
    private String plateNumber;
    private String checkInTime;
    private String checkOutTime;
    private String status;
}
