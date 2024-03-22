package pjmanagement.projectmanagement.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {


    private String username;
    private String email;
    private String role;
    private String password;



    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;

    public void setMessage(String userRegisteredSuccessfully) {
    }

    public void setStatusCode(int i) {
    }

    public void setError(String message) {
    }
}
