package parkingpos.checkout_service.service.contract;

import parkingpos.checkout_service.dto.CheckoutRequestDTO;
import parkingpos.checkout_service.dto.CheckoutResponseDTO;
import parkingpos.checkout_service.dto.CheckoutPreviewDTO;

public interface CheckoutService {
    
    CheckoutResponseDTO checkout(CheckoutRequestDTO request);

    CheckoutPreviewDTO getCheckoutPreview(String plateNumber);
    
}
