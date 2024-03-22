package pjmanagement.projectmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.services.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto signInRequest){
        return ResponseEntity.ok(authService.login(signInRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDto> logout() {
        return ResponseEntity.ok(authService.logout());
    }

}
