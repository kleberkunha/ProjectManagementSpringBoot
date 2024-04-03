package pjmanagement.projectmanagement.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private UserDto user;

    private int statusCode;
}