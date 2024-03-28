package pjmanagement.projectmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.services.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto registerRequest) {

        UserDto registeredUser = authService.register(registerRequest);

        if (registeredUser.getStatusCode() == HttpStatus.OK.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registeredUser);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto signInRequest) {
        UserDto loggedInUser = authService.login(signInRequest);

        if (loggedInUser != null && loggedInUser.getStatusCode() == 200) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loggedInUser);
        }
    }





/*    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto signInRequest){
        UserDto loggedInUser = authService.login(signInRequest);

        if(loggedInUser != null && loggedInUser.getStatusCode() == 200) {
            return ResponseEntity.ok(loggedInUser);
        }else{
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loggedInUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loggedInUser);
    }*/

    @PostMapping("/logout")
    public ResponseEntity<UserDto> logout() {
        return ResponseEntity.ok(authService.logout());
    }

}
