package pjmanagement.projectmanagement.services;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pjmanagement.projectmanagement.dto.JwtAuthenticationResponse;
import pjmanagement.projectmanagement.utils.UserMapper;



import lombok.RequiredArgsConstructor;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.RoleEntity;
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
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEntity.ROLE_USER)
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }



    public JwtAuthenticationResponse login(UserDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String token = jwtService.generateToken(user);

        // Map UserEntity to UserDto
        UserDto userDto = UserMapper.toDto(user);

        // Create JwtAuthenticationResponse with token and user data
        return JwtAuthenticationResponse.builder()
                .token(token)
                .user(userDto)
                .build();
    }

/*    public JwtAuthenticationResponse login(UserDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }*/

}