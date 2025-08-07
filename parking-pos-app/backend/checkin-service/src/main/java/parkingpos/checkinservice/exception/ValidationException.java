package parkingpos.checkinservice.exception;

public class ValidationException extends RuntimeException {
    private final String code;

    public ValidationException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
    
}
