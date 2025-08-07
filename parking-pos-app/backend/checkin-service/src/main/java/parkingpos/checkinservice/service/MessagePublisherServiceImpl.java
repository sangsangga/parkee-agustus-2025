package parkingpos.checkinservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import parkingpos.checkinservice.config.RabbitConfig;
import parkingpos.checkinservice.dto.messaging.CheckinMessageDTO;
import parkingpos.checkinservice.service.contract.MessagePublisherService;

@Service
@Slf4j
public class MessagePublisherServiceImpl implements MessagePublisherService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessagePublisherServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    
    @Override
    public void publishCheckinRequest(CheckinMessageDTO checkinMessageDTO) {
        try {
            log.info("Publishing checkin request: {}", checkinMessageDTO);
            rabbitTemplate.convertAndSend(
                RabbitConfig.PARKING_EXCHANGE,
                RabbitConfig.CHECKIN_ROUTING_KEY,
                checkinMessageDTO
            );
            log.info("Checkin request published successfully");
        } catch (Exception e) {
            log.error("Error publishing checkin request: {}", e.getMessage());
            throw new RuntimeException("Error publishing checkin request", e);
        }
        
    
    }
    
}
