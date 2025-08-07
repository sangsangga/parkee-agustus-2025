package parkingpos.checkinservice.service.contract;

import parkingpos.checkinservice.dto.messaging.CheckinMessageDTO;

public interface MessagePublisherService {
    void publishCheckinRequest(CheckinMessageDTO checkinMessageDTO);
}
