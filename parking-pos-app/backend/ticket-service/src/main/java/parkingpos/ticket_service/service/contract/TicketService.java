package parkingpos.ticket_service.service.contract;

import parkingpos.ticket_service.dto.CreateTicketRequestDTO;
import parkingpos.ticket_service.dto.CreateTicketResponseDTO;
import parkingpos.ticket_service.dto.TicketResponseDTO;
import parkingpos.ticket_service.dto.UpdateTicketRequestDTO;

public interface TicketService {
    CreateTicketResponseDTO createTicket(CreateTicketRequestDTO request);
    TicketResponseDTO getActiveTicketByPlateNumber(String plateNumber);
    TicketResponseDTO updateTicketCheckout(Long ticketId, UpdateTicketRequestDTO request);
} 