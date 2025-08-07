package parkingpos.checkinservice.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import parkingpos.checkinservice.dto.CreateTicketRequestDTO;
import parkingpos.checkinservice.dto.CreateTicketResponseDTO;
import parkingpos.checkinservice.repository.TicketRepository;
import parkingpos.checkinservice.service.contract.CheckinService;

class CheckinServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CheckinService checkinService;
    

    @Test
    void shouldCreateTicketWhenCheckingIn() {
        CreateTicketRequestDTO request = new CreateTicketRequestDTO();
        request.setPlateNumber("B12345FFF");

        CreateTicketResponseDTO response = checkinService.checkIn(request);

        assertNotNull(response);
        assertEquals(request.getPlateNumber(), response.getPlateNumber());
        assertNotNull(response.getCheckInTime());
        assertNotNull(response.getId());
        assertNull(response.getCheckOutTime());
    }


    // @Test
    // void shouldThrowExceptionWhenPlateNumberIsEmpty() {
    //     CreateTicketRequestDTO request = new CreateTicketRequestDTO();

    //     CreateTicketResponseDTO response = checkinService.checkIn(request);

    //     assertThrows(null, null)
    // }


    

} 