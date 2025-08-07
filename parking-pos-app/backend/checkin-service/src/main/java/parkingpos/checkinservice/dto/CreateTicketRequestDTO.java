package parkingpos.checkinservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTicketRequestDTO {
    @NotBlank(message = "Please Provide Plate Number")
    String plateNumber;
}
