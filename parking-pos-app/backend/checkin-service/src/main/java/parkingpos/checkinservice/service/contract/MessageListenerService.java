package parkingpos.checkinservice.service.contract;

import parkingpos.checkinservice.dto.messaging.TicketCreatedMessageDTO;

public interface MessageListenerService {
    void handleCreateTicketResponse(TicketCreatedMessageDTO dto);
    
}
