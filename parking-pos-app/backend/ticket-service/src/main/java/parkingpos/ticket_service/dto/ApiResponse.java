package parkingpos.ticket_service.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;

    public static <T> ApiResponse<T> ok(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }
} 