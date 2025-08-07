package parkingpos.ticket_service.dto.Messaging;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinMessageDTO {
    private String plateNumber;
    private ZonedDateTime requestTime;
    private String source;
    private String correlationId;   
}
