package parkingpos.checkout_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import parkingpos.checkout_service.dto.ApiResponse;
import parkingpos.checkout_service.dto.CheckoutRequestDTO;
import parkingpos.checkout_service.dto.CheckoutResponseDTO;
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
    
}
