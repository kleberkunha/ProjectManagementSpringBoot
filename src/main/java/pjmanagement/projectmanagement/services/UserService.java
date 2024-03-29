package pjmanagement.projectmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public UserEntity save(UserEntity newUser) {
        if (newUser.getId() != null) {
            newUser.setUpdatedAt(LocalDateTime.now());
        } else {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        return userRepository.save(newUser);
    }


}
