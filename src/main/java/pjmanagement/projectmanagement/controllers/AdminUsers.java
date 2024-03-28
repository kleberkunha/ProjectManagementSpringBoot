package pjmanagement.projectmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pjmanagement.projectmanagement.dto.ProjectDto;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.ProjectEntity;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.ProjectRepository;
import pjmanagement.projectmanagement.repository.UserRepository;
import pjmanagement.projectmanagement.utils.UserMapper;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdminUsers {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public AdminUsers(ProjectRepository projectRepository, UserRepository userRepository, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @PostMapping("/admin/saveproject")
    public ResponseEntity<Object> register(@RequestBody ProjectDto projectRequest) {
        ProjectEntity projectToSave = new ProjectEntity();
        projectToSave.setName(projectRequest.getName());
        // Further processing
        return ResponseEntity.ok(projectRepository.save(projectToSave));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        if (userDtos.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no users found
        }

        return ResponseEntity.ok(userDtos); // Return the list of users
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        Optional<UserEntity> userFoundById = userRepository.findById(id);

        if (userFoundById.isPresent()) {
            UserDto userDto = userMapper.toDto(userFoundById.get());
            return ResponseEntity.ok(userDto); // Return the user DTO if found
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if user not found
        }
    }

    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone(){
        return ResponseEntity.ok("USers alone can access this ApI only");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminaAndUsersApi(){
        return ResponseEntity.ok("Both Admin and Users Can  access the api");
    }

    /** You can use this to get the details(name,email,role,ip, e.t.c) of user accessing the service*/
    @GetMapping("/public/email")
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication); //get all details(name,email,password,roles e.t.c) of the user
        System.out.println(authentication.getDetails()); // get remote ip
        System.out.println(authentication.getName()); //returns the email because the email is the unique identifier
        return authentication.getName(); // returns the email
    }
}
