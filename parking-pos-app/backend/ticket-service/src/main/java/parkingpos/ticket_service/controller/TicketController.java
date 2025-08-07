package parkingpos.ticket_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import parkingpos.ticket_service.dto.ApiResponse;
import parkingpos.ticket_service.dto.CreateTicketRequestDTO;
import parkingpos.ticket_service.dto.CreateTicketResponseDTO;
import parkingpos.ticket_service.dto.TicketResponseDTO;
import parkingpos.ticket_service.dto.UpdateTicketRequestDTO;
import parkingpos.ticket_service.service.contract.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ApiResponse<CreateTicketResponseDTO> createTicket(@RequestBody CreateTicketRequestDTO request) {
        return ApiResponse.ok(ticketService.createTicket(request), "Ticket created successfully");
    }

    @GetMapping("/active/{plateNumber}")
    public ApiResponse<TicketResponseDTO> getActiveTicket(@PathVariable String plateNumber) {
        return ApiResponse.ok(ticketService.getActiveTicketByPlateNumber(plateNumber), "Active ticket found");
    }

    @PutMapping("/{ticketId}/checkout")
    public ApiResponse<TicketResponseDTO> updateTicketCheckout(@PathVariable Long ticketId, @RequestBody @Valid UpdateTicketRequestDTO request) {
        return ApiResponse.ok(ticketService.updateTicketCheckout(ticketId, request), "Ticket checkout updated successfully");
    }
} 