package parkingpos.checkinservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import parkingpos.checkinservice.dto.CreateTicketRequestDTO;
import parkingpos.checkinservice.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
class CheckinServiceTest {
    
    @Mock
    private WebClient webClient;

    @InjectMocks
    private CheckinServiceImpl checkinService;

    @Test
    void shouldThrowExceptionWhenPlateNumberIsEmpty() {
        CreateTicketRequestDTO request = new CreateTicketRequestDTO();
        request.setPlateNumber("");

        assertThrows(ValidationException.class, () -> {
            checkinService.checkIn(request);
        });
    }

    @Test
    void shouldThrowExceptionWhenPlateNumberIsNull() {
        CreateTicketRequestDTO request = new CreateTicketRequestDTO();
        request.setPlateNumber(null);

        assertThrows(ValidationException.class, () -> {
            checkinService.checkIn(request);
        });
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNull() {
        assertThrows(ValidationException.class, () -> {
            checkinService.checkIn(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenPlateNumberHasInvalidCharacters() {
        CreateTicketRequestDTO request = new CreateTicketRequestDTO();
        request.setPlateNumber("ABC-123");

        assertThrows(ValidationException.class, () -> {
            checkinService.checkIn(request);
        });
    }
} 