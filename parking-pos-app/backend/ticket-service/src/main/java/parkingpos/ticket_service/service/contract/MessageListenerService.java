package parkingpos.ticket_service.service.contract;

import parkingpos.ticket_service.dto.Messaging.CheckinMessageDTO;

public interface MessageListenerService {
    void handleCheckinRequest(CheckinMessageDTO message);
}
