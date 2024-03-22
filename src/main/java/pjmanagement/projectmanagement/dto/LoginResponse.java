package pjmanagement.projectmanagement.dto;

import pjmanagement.projectmanagement.entities.UserEntity;

public class LoginResponse {
    private String token;
    private String username;

    private String email;

    public LoginResponse(String token, UserEntity userEntity){
        this.token = token;
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
    }
}
