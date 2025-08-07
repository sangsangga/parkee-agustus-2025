package parkingpos.ticket_service.service;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import parkingpos.ticket_service.dto.CreateTicketRequestDTO;
import parkingpos.ticket_service.dto.CreateTicketResponseDTO;
import parkingpos.ticket_service.dto.TicketResponseDTO;
import parkingpos.ticket_service.dto.UpdateTicketRequestDTO;
import parkingpos.ticket_service.exception.ValidationException;
import parkingpos.ticket_service.model.Ticket;
import parkingpos.ticket_service.repository.TicketRepository;
import parkingpos.ticket_service.service.contract.TicketService;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO request) {
        validateCreateTicketPayload(request);

        Ticket ticket = buildTicket(request);

        if (ticketRepository.existsActiveByPlateNumber(ticket.getPlateNumber())) {
            log.warn("Plate Number is already active: {}", ticket.getPlateNumber());
            throw new ValidationException("Plate Number is already active", "TICKET-005");
        }

        try {
            ticketRepository.save(ticket);
            log.info("Successfully created ticket for plate number: {}", ticket.getPlateNumber());
            return buildResponse(ticket);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to create ticket", e);
            throw new ValidationException("Failed to create ticket", "TICKET-006");
        }


    }

    private Ticket buildTicket(CreateTicketRequestDTO request) {
        Ticket ticket = new Ticket();
        ticket.setPlateNumber(request.getPlateNumber().trim().toUpperCase());
        ticket.setCheckInTime(ZonedDateTime.now());
        ticket.setStatus("Active");
        return ticket;
    }

    private CreateTicketResponseDTO buildResponse(Ticket ticket) {
        CreateTicketResponseDTO response = new CreateTicketResponseDTO();
        response.setId(ticket.getId());
        response.setPlateNumber(ticket.getPlateNumber());
        response.setCheckInTime(ticket.getCheckInTime());
        response.setStatus(ticket.getStatus());
        return response;
    }


    private void validateCreateTicketPayload(CreateTicketRequestDTO request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new ValidationException("Please Provide Plate Number", "TICKET-000");
        }

        if (ObjectUtils.isEmpty(request.getPlateNumber())) {
            throw new ValidationException("Please Provide Plate Number", "TICKET-001");
        }

        String plateNumber = request.getPlateNumber().trim();

        if (plateNumber.isEmpty()) {
            throw new ValidationException("Plate Number cannot be empty", "TICKET-002");
        }
        
        if (plateNumber.length() > 20) {
            throw new ValidationException("Plate Number is too long (max 20 characters)", "TICKET-007");
        }
    }

    @Override
    public TicketResponseDTO getActiveTicketByPlateNumber(String plateNumber) {
        log.info("Getting active ticket for plate number: {}", plateNumber);
        
        if (ObjectUtils.isEmpty(plateNumber)) {
            throw new ValidationException("Please provide plate number", "TICKET-008");
        }

        String normalizedPlateNumber = plateNumber.trim().toUpperCase();
        
        Ticket ticket = ticketRepository.findActiveByPlateNumber(normalizedPlateNumber)
            .orElseThrow(() -> new ValidationException("No active ticket found for plate number: " + normalizedPlateNumber, "TICKET-009"));
        
        return buildTicketResponse(ticket);
    }

    @Override
    @Transactional
    public TicketResponseDTO updateTicketCheckout(Long ticketId, UpdateTicketRequestDTO request) {
        log.info("Updating ticket checkout for ticket ID: {}", ticketId);
        
        if (ObjectUtils.isEmpty(ticketId)) {
            throw new ValidationException("Please provide ticket ID", "TICKET-010");
        }

        if (ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(request.getCheckOutTime())) {
            throw new ValidationException("Please provide checkout time", "TICKET-011");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ValidationException("Ticket not found with ID: " + ticketId, "TICKET-012"));

        if (!"Active".equals(ticket.getStatus())) {
            throw new ValidationException("Ticket is not active", "TICKET-013");
        }

        ticket.setCheckOutTime(request.getCheckOutTime());
        ticket.setStatus("Completed");
        
        ticketRepository.save(ticket);
        log.info("Successfully updated ticket checkout for ticket ID: {}", ticketId);
        
        return buildTicketResponse(ticket);
    }

    private TicketResponseDTO buildTicketResponse(Ticket ticket) {
        TicketResponseDTO response = new TicketResponseDTO();
        response.setId(ticket.getId());
        response.setPlateNumber(ticket.getPlateNumber());
        response.setCheckInTime(ticket.getCheckInTime());
        response.setCheckOutTime(ticket.getCheckOutTime());
        response.setStatus(ticket.getStatus());
        return response;
    }

} 