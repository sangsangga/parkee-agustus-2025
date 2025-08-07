package parkingpos.checkinservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import parkingpos.checkinservice.dto.messaging.TicketCreatedMessageDTO;
import parkingpos.checkinservice.service.contract.MessageListenerService;

@Service
@Slf4j
public class MessageListenerServiceImpl implements MessageListenerService {
    
    
    
    
    @Override
    @RabbitListener(queues = "ticket.response.queue")
    public void handleCreateTicketResponse(TicketCreatedMessageDTO message) {
        // TODO Auto-generated method stub
        log.info("Received ticket created response: {}", message);
        log.info("Ticket {} created for plate {} with status {}", 
            message.getTicketId(), 
            message.getPlateNumber(), 
            message.getStatus()
        );
        
    }



}
