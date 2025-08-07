package parkingpos.checkinservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import parkingpos.checkinservice.dto.ApiResponse;
import parkingpos.checkinservice.dto.CreateTicketRequestDTO;
import parkingpos.checkinservice.dto.CreateTicketResponseDTO;
import parkingpos.checkinservice.service.contract.CheckinService;

@RestController
@RequestMapping("/checkin")
public class CheckinController {

    
    private final CheckinService checkinService;

    @Autowired
    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @PostMapping
    public ApiResponse<CreateTicketResponseDTO> checkIn(@RequestBody   @Valid CreateTicketRequestDTO payload){
        return ApiResponse.ok(checkinService.checkIn(payload), "Success");
    }
    
}
