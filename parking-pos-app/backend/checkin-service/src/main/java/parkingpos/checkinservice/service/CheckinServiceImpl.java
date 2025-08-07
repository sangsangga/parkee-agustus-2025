package parkingpos.checkinservice.service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import parkingpos.checkinservice.dto.CreateTicketRequestDTO;
import parkingpos.checkinservice.dto.CreateTicketResponseDTO;
import parkingpos.checkinservice.dto.messaging.CheckinMessageDTO;
import parkingpos.checkinservice.dto.ApiResponse;
import parkingpos.checkinservice.exception.ValidationException;
import parkingpos.checkinservice.service.contract.CheckinService;
import parkingpos.checkinservice.service.contract.MessagePublisherService;

@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {

    private final MessagePublisherService messagePublisherService;
    private final WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public CheckinServiceImpl(
        MessagePublisherService messagePublisherService,
        WebClient webClient
    ) {

        this.messagePublisherService = messagePublisherService;
        this.webClient = webClient;
    }

    @Override
    public CreateTicketResponseDTO checkIn(CreateTicketRequestDTO payload) {
        log.info("Creating ticket for plate number: {}", payload.getPlateNumber());
        validateCreateCheckinPayload(payload);

        String correlationId = UUID.randomUUID().toString();

        CheckinMessageDTO message = new CheckinMessageDTO(
            payload.getPlateNumber().trim().toUpperCase(),
            ZonedDateTime.now(),
            "checkin-service",
            correlationId
        );

        try {
            ApiResponse<CreateTicketResponseDTO> response = webClient
                .post()
                .uri("/tickets")
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ApiResponse.class)
                        .map(errorResponse -> new ValidationException(
                            errorResponse.getMessage(), 
                            errorResponse.getCode()
                        ))
                )
                .bodyToMono(ApiResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
            
            if (response != null && response.isSuccess()) {
                CreateTicketResponseDTO ticketResponse = convertToTicketResponse(response.getData());
                log.info("Success create ticket for plate number: {}", ticketResponse.getPlateNumber());
                return ticketResponse;
            }

            throw new ValidationException("Failed to create ticket: " + response.getMessage(), "CHECKIN-004");
            
        } catch (ValidationException e) {
            // Re-throw ValidationException to preserve the original error message
            throw e;
        } catch (Exception e) {
            log.error("Failed process message checkin {}", e.getMessage());
            throw new ValidationException("Failed to process checkin", "CHECKIN-004");
        }
    }

    private CreateTicketResponseDTO convertToTicketResponse(Object Data) {
        try {
            return objectMapper.convertValue(Data, CreateTicketResponseDTO.class);
        } catch (Exception e) {
            throw new ValidationException("Failed to Create Ticket", "CHECKIN-005");
        }
    }


    private void validateCreateCheckinPayload(CreateTicketRequestDTO payload) {
        if (ObjectUtils.isEmpty(payload)) {
            throw new ValidationException("Please Provide Plate Number", "CHECKIN-000");
        }

        if (ObjectUtils.isEmpty(payload.getPlateNumber())) {
            throw new ValidationException("Please Provide Plate Number", "CHECKIN-001");
        }

        String plateNumber = payload.getPlateNumber().trim();

        if (!plateNumber.matches("^[A-Z0-9]+$")) {
            throw new ValidationException("Plate Number must contain only letters and numbers", "CHECKIN-002");
        }
    }
}
