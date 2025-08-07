package parkingpos.checkinservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import parkingpos.checkinservice.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(ValidationException ex) {
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), ex.getCode());
        return ResponseEntity.badRequest().body(response);
    }

} 