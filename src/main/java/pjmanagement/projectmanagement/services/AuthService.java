package pjmanagement.projectmanagement.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;

import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto register(UserDto registrationRequest) {

        if (registrationRequest.getEmail() == null || registrationRequest.getPassword() == null) {
            registrationRequest.setStatusCode(HttpStatus.BAD_REQUEST.value());
            registrationRequest.setMessage("Please provide an email and password");
            return registrationRequest;
        }

        // Check if the email already exists
        var existingUserOptional = userRepository.findByEmail(registrationRequest.getEmail());
        if (existingUserOptional.isPresent()) {
            registrationRequest.setStatusCode(HttpStatus.BAD_REQUEST.value());
            registrationRequest.setMessage("Email already exists");
            return registrationRequest;
        }

        // Create a new user
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registrationRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepository.save(userEntity);

        // Set response for successful registration
        registrationRequest.setStatusCode(HttpStatus.OK.value());
        registrationRequest.setMessage("User registered successfully");

        return registrationRequest;
    }


    public UserDto login(UserDto loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (user != null && encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            loginRequest.setStatusCode(HttpStatus.OK.value());
            loginRequest.setToken(jwt);
            loginRequest.setId(user.getId());
            loginRequest.setRefreshToken(refreshToken);
            loginRequest.setExpirationTime("24Hr");
            loginRequest.setMessage("Successfully Signed In");
        } else if (user == null) {
            // User not found
            loginRequest.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            loginRequest.setMessage("Invalid email or password");
        } else {
            // Password incorrect
            loginRequest.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            loginRequest.setMessage("Invalid email or password");
        }

        return loginRequest;
    }


    public UserDto logout() {
        // Clear token and refresh token fields
        UserDto userDto = new UserDto();
        userDto.setToken(null);
        userDto.setRefreshToken(null);
        userDto.setExpirationTime(null);
        userDto.setMessage("Successfully Logged Out");
        userDto.setStatusCode(200);

        return userDto;

        /*return "redirect:/home";*/
    }



}