package pjmanagement.projectmanagement.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;

import java.util.HashMap;

@Service
public class AuthService {

    private UserRepository userRepository;

    private JWTUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserDto register(UserDto registrationRequest) {
        UserDto newUser = new UserDto();

        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(registrationRequest.getEmail());
            userEntity.setUsername(registrationRequest.getUsername());
            userEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            userEntity.setRole(registrationRequest.getRole());

            userRepository.save(userEntity);

            // Set properties for the new user DTO
            newUser.setEmail(userEntity.getEmail());
            newUser.setUsername(userEntity.getUsername());
            newUser.setRole(userEntity.getRole());
            newUser.setMessage("User registered successfully");
        } catch (Exception e) {
            newUser.setStatusCode(500);
            newUser.setError(e.getMessage());
        }

        return newUser;
    }

    public UserDto login(UserDto loginRequest) {

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        if(user != null){
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            loginRequest.setStatusCode(200);
            loginRequest.setToken(jwt);
            loginRequest.setRefreshToken(refreshToken);
            loginRequest.setExpirationTime("24Hr");
            loginRequest.setMessage("Successfully Signed In");
        }else {
            loginRequest.setStatusCode(500);
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