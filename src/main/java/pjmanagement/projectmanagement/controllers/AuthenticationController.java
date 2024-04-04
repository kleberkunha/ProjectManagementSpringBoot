package pjmanagement.projectmanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import pjmanagement.projectmanagement.dto.JwtAuthenticationResponse;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;
import pjmanagement.projectmanagement.services.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody UserDto request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody UserDto request) {
        return authenticationService.login(request);
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        // Retrieve all users from the database
        return userRepository.findAll();
    }

    @GetMapping(path = {"/profile/{id}"})
    public UserEntity findOne(@PathVariable("id") int id){
        return userRepository.findById(id);
    }


}
