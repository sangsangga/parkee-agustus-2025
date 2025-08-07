package parkingpos.checkout_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import parkingpos.checkout_service.dto.ApiResponse;
import parkingpos.checkout_service.dto.CheckoutRequestDTO;
import parkingpos.checkout_service.dto.CheckoutResponseDTO;
import parkingpos.checkout_service.dto.TicketDTO;
import parkingpos.checkout_service.service.contract.CheckoutService;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ApiResponse<CheckoutResponseDTO> checkout(@RequestBody @Valid CheckoutRequestDTO request) {
        CheckoutResponseDTO response = checkoutService.checkout(request);
        return ApiResponse.ok(response, "Checkout completed successfully");
    }

    @GetMapping("/tickets/active/{plateNumber}")
    public ApiResponse<TicketDTO> getActiveTicket(@PathVariable String plateNumber) {
        TicketDTO response = checkoutService.getActiveTicketPreview(plateNumber);
        return ApiResponse.ok(response, "Active ticket found");
    }
    
}
