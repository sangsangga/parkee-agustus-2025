package parkingpos.ticket_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import parkingpos.ticket_service.config.RabbitConfig;
import parkingpos.ticket_service.dto.CreateTicketRequestDTO;
import parkingpos.ticket_service.dto.CreateTicketResponseDTO;
import parkingpos.ticket_service.dto.Messaging.CheckinMessageDTO;
import parkingpos.ticket_service.dto.Messaging.TicketCreatedMessageDTO;
import parkingpos.ticket_service.service.contract.MessageListenerService;
import parkingpos.ticket_service.service.contract.TicketService;

@Service
@Slf4j
public class MessageListenerServiceImpl implements MessageListenerService {

    private final TicketService ticketService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageListenerServiceImpl (TicketService ticketService, RabbitTemplate rabbitTemplate) {
        this.ticketService = ticketService;
        this.rabbitTemplate = rabbitTemplate;
    }
    

    @Override
    @RabbitListener(queues = "checkin.queue")
    public void handleCheckinRequest(CheckinMessageDTO message) {
        log.info("Received checkin request: {}", message);
    
        try {
            CreateTicketRequestDTO dto = new CreateTicketRequestDTO();
            dto.setPlateNumber(message.getPlateNumber());

            CreateTicketResponseDTO result = ticketService.createTicket(dto);

            TicketCreatedMessageDTO ticketCreatedMessage = new TicketCreatedMessageDTO(
                result.getId(),
                result.getPlateNumber(),
                result.getCheckInTime(),
                result.getStatus(),
                message.getCorrelationId()
            );

            rabbitTemplate.convertAndSend(
                RabbitConfig.PARKING_EXCHANGE,
                RabbitConfig.TICKET_RESPONSE_ROUTING_KEY,
                ticketCreatedMessage
            );

            log.info("Success create ticket for {}", ticketCreatedMessage);
            
            
        } catch (Exception e) {
            log.error("Failed to process checkin request for :{}",  message.getPlateNumber());
        }
    }

    
}
