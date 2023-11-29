package tech.thapelomalifi.jwtpractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.thapelomalifi.jwtpractice.dto.LoginDTO;
import tech.thapelomalifi.jwtpractice.dto.TokenResponseDTO;
import tech.thapelomalifi.jwtpractice.model.User;
import tech.thapelomalifi.jwtpractice.repository.UserRepository;
import tech.thapelomalifi.jwtpractice.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping(path = "/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.signUpUser(user), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path ="/secured")
    public String securedEndpoint() {
        return "You are logged in";
    }
}
