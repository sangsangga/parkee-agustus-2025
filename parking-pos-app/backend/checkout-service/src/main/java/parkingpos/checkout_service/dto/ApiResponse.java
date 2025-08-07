package parkingpos.checkout_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String code;
    private T data;


    public static <T> ApiResponse<T> ok (T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setData(data);
        response.setSuccess(true);

        return response;
    }

    public static <T> ApiResponse<T> error (String message, String code) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage(message);
        response.setCode(code);
        response.setSuccess(false);

        return response;
    }
}
