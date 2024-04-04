package pjmanagement.projectmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

            //Test of population
            newUser.setFirstName(newUser.getFirstName());
            newUser.setLastName(newUser.getLastName());
            newUser.setRole(newUser.getRole());
        } else {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        return userRepository.save(newUser);
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity updateUser(Long id, UserEntity updatedUserData) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity existingUser = optionalUser.get();
            existingUser.setFirstName(updatedUserData.getFirstName());
            existingUser.setLastName(updatedUserData.getLastName());
            existingUser.setRole(updatedUserData.getRole());
            existingUser.setEmail(updatedUserData.getEmail());
            // Set other fields that you want to update

            // Set updated timestamp
            existingUser.setUpdatedAt(LocalDateTime.now());

            return userRepository.save(existingUser);
        } else {
            // Handle case where user with given ID is not found
            throw new IllegalArgumentException(STR."User with ID \{id} not found");
        }
    }




}
