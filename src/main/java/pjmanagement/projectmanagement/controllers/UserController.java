package pjmanagement.projectmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pjmanagement.projectmanagement.dto.RegisterForm;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.services.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterForm registerForm) {
        /*I need to create the register for using the data coming from flutter app */
        try {
            userService.registerUser(registerForm.getUsername(), registerForm.getPassword(), registerForm.getEmail());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}
