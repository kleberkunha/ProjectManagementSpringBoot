package pjmanagement.projectmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pjmanagement.projectmanagement.dto.ProjectDto;
import pjmanagement.projectmanagement.entities.ProjectEntity;
import pjmanagement.projectmanagement.entities.UserEntity;
import pjmanagement.projectmanagement.repository.ProjectRepository;
import pjmanagement.projectmanagement.repository.UserRepository;

import java.util.List;

@RestController
public class AdminUsers {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public AdminUsers(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no users found
        }

        return ResponseEntity.ok(users); // Return the list of users
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
