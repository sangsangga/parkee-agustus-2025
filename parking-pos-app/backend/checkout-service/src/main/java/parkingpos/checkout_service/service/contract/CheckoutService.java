package parkingpos.checkout_service.service.contract;

import parkingpos.checkout_service.dto.CheckoutRequestDTO;
import parkingpos.checkout_service.dto.CheckoutResponseDTO;

public interface CheckoutService {
    
    CheckoutResponseDTO checkout(CheckoutRequestDTO request);
    
}
