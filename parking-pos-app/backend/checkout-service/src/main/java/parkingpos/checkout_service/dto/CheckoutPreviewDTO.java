package parkingpos.checkout_service.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class CheckoutPreviewDTO {
    
    private Long ticketId;
    private String plateNumber;
    private ZonedDateTime checkInTime;
    private ZonedDateTime estimatedCheckOutTime;
    private long estimatedDurationMinutes;
    private BigDecimal estimatedPrice;
    private String status;
    
}
