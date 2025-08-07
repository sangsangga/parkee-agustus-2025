package parkingpos.checkout_service.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import parkingpos.checkout_service.dto.ApiResponse;
import parkingpos.checkout_service.dto.CheckoutRequestDTO;
import parkingpos.checkout_service.dto.CheckoutResponseDTO;
import parkingpos.checkout_service.dto.TicketDTO;
import parkingpos.checkout_service.exception.ValidationException;
import parkingpos.checkout_service.service.contract.CheckoutService;

@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private static final BigDecimal HOURLY_RATE = new BigDecimal("3000");

    @Autowired
    public CheckoutServiceImpl(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public CheckoutResponseDTO checkout(CheckoutRequestDTO request) {
        log.info("Processing checkout for plate number: {}", request.getPlateNumber());
        validateCheckoutPayload(request);

        TicketDTO activeTicket = getActiveTicket(request.getPlateNumber().trim().toUpperCase());
        
        ZonedDateTime checkOutTime = ZonedDateTime.now();
        CheckoutResponseDTO response = calculateCheckoutDetails(activeTicket, checkOutTime);
        
        updateTicketStatus(activeTicket.getId(), checkOutTime);
        
        log.info("Successfully processed checkout for plate number: {} with total price: {}", 
            response.getPlateNumber(), response.getTotalPrice());
        
        return response;
    }

    private void validateCheckoutPayload(CheckoutRequestDTO request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new ValidationException("Please provide plate number", "CHECKOUT-000");
        }

        if (ObjectUtils.isEmpty(request.getPlateNumber())) {
            throw new ValidationException("Please provide plate number", "CHECKOUT-001");
        }

        String plateNumber = request.getPlateNumber().trim();

        if (plateNumber.isEmpty()) {
            throw new ValidationException("Plate number cannot be empty", "CHECKOUT-002");
        }
        
        if (plateNumber.length() > 20) {
            throw new ValidationException("Plate number is too long (max 20 characters)", "CHECKOUT-003");
        }
    }

    @Override
    public TicketDTO getActiveTicketPreview(String plateNumber) {
        TicketDTO ticket = getActiveTicket(plateNumber);
        if (ticket == null) {
            throw new ValidationException("No active ticket found", "CHECKOUT-004");
        }
        return ticket;
    }

    private TicketDTO getActiveTicket(String plateNumber) {
        try {
            ApiResponse<?> response = webClient
                .get()
                .uri("/tickets/active/{plateNumber}", plateNumber)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ApiResponse.class)
                        .map(errorResponse -> new ValidationException(
                            errorResponse.getMessage(), 
                            "CHECKOUT-004"
                        ))
                )
                .bodyToMono(ApiResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();

            if (response != null && response.isSuccess()) {
                return convertToTicketDTO(response.getData());
            }

            throw new ValidationException("Failed to get active ticket: " + response.getMessage(), "CHECKOUT-004");

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to get active ticket for plate number: {}", plateNumber, e);
            throw new ValidationException("Failed to process checkout", "CHECKOUT-005");
        }
    }

    private void updateTicketStatus(Long ticketId, ZonedDateTime checkOutTime) {
        try {
            ApiResponse<?> response = webClient
                .put()
                .uri("/tickets/{ticketId}/checkout", ticketId)
                .bodyValue(java.util.Map.of("checkOutTime", checkOutTime))
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ApiResponse.class)
                        .map(errorResponse -> new ValidationException(
                            errorResponse.getMessage(), 
                            "CHECKOUT-006"
                        ))
                )
                .bodyToMono(ApiResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();

            if (response == null || !response.isSuccess()) {
                log.warn("Failed to update ticket status for ticket ID: {}", ticketId);
            }

        } catch (Exception e) {
            log.error("Failed to update ticket status for ticket ID: {}", ticketId, e);
        }
    }

    private CheckoutResponseDTO calculateCheckoutDetails(TicketDTO ticket, ZonedDateTime checkOutTime) {
        CheckoutResponseDTO response = new CheckoutResponseDTO();
        
        response.setTicketId(ticket.getId());
        response.setPlateNumber(ticket.getPlateNumber());
        response.setCheckInTime(ticket.getCheckInTime());
        response.setCheckOutTime(checkOutTime);
        response.setStatus("Completed");
        
        // Calculate parking duration in minutes
        long durationMinutes = Duration.between(ticket.getCheckInTime(), checkOutTime).toMinutes();
        response.setParkingDurationMinutes(durationMinutes);
        
        // Calculate total price based on hourly rate (3000 per hour)
        // Minimum charge is for 1 hour, then charge per hour (rounded up)
        BigDecimal hours = BigDecimal.valueOf(durationMinutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.CEILING);
        if (hours.compareTo(BigDecimal.ONE) < 0) {
            hours = BigDecimal.ONE; // Minimum 1 hour charge
        } else {
            hours = hours.setScale(0, RoundingMode.CEILING); // Round up to nearest hour
        }
        
        BigDecimal totalPrice = HOURLY_RATE.multiply(hours);
        response.setTotalPrice(totalPrice);
        
        return response;
    }

    private TicketDTO convertToTicketDTO(Object data) {
        try {
            return objectMapper.convertValue(data, TicketDTO.class);
        } catch (Exception e) {
            throw new ValidationException("Failed to process ticket data", "CHECKOUT-007");
        }
    }
}
