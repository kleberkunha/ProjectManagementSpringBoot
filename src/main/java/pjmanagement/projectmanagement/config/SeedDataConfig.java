package pjmanagement.projectmanagement.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pjmanagement.projectmanagement.entities.RoleEntity;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.UserRepository;
import pjmanagement.projectmanagement.services.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            UserEntity admin = UserEntity
                    .builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("password"))
                    .role(RoleEntity.ROLE_ADMIN)
                    .build();

            userService.save(admin);
            log.debug("created ADMIN user - {}", admin);
        }
    }

}