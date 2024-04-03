package pjmanagement.projectmanagement.services;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pjmanagement.projectmanagement.dto.JwtAuthenticationResponse;
import pjmanagement.projectmanagement.utils.UserMapper;



import lombok.RequiredArgsConstructor;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse register(UserDto request) {
        var user = UserEntity
                .builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }



    public JwtAuthenticationResponse login(UserDto request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            // Set authentication in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Find user by email
            UserEntity user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

            // Generate JWT token
            String token = jwtService.generateToken(user);

            // Map UserEntity to UserDto
            UserDto userDto = UserMapper.toDto(user);

            // Create JwtAuthenticationResponse with token, user data, and success status code
            return new JwtAuthenticationResponse(token,userDto, 200); // Assuming 200 represents success status code
        } catch (Exception e) {
            // If authentication fails, return failure status code
            return new JwtAuthenticationResponse(null, null, 401); // Assuming 401 represents unauthorized status code
        }
    }

}