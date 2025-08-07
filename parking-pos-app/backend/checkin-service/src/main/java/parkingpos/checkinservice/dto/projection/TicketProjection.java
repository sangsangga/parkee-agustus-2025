package parkingpos.checkinservice.dto.projection;

import java.time.ZonedDateTime;

public interface TicketProjection {
    Long getId();
    String getPlateNumber();
    ZonedDateTime getCheckInTime();
    ZonedDateTime getCheckOutTime();
    String getStatus();
}
