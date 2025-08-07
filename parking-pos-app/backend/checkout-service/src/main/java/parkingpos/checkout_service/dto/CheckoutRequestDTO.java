package parkingpos.checkout_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckoutRequestDTO {
    
    @NotBlank(message = "Plate number is required")
    private String plateNumber;
    
}
