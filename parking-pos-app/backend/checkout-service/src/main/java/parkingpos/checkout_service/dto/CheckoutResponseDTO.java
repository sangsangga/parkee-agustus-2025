package parkingpos.checkout_service.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class CheckoutResponseDTO {
    
    private Long ticketId;
    private String plateNumber;
    private ZonedDateTime checkInTime;
    private ZonedDateTime checkOutTime;
    private long parkingDurationMinutes;
    private BigDecimal totalPrice;
    private String status;
    
}
