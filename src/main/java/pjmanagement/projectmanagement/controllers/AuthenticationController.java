package pjmanagement.projectmanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pjmanagement.projectmanagement.dto.JwtAuthenticationResponse;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.services.AuthenticationService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody UserDto request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody UserDto request) {
        return authenticationService.login(request);
    }
}
