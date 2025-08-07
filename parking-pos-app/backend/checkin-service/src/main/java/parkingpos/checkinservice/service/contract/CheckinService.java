package parkingpos.checkinservice.service.contract;

import parkingpos.checkinservice.dto.CreateTicketRequestDTO;
import parkingpos.checkinservice.dto.CreateTicketResponseDTO;

public interface CheckinService {
    CreateTicketResponseDTO checkIn(CreateTicketRequestDTO payload);
}
