package tech.thapelomalifi.jwtpractice.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.thapelomalifi.jwtpractice.dto.LoginDTO;
import tech.thapelomalifi.jwtpractice.model.MyUserDetails;
import tech.thapelomalifi.jwtpractice.dto.TokenResponseDTO;
import tech.thapelomalifi.jwtpractice.model.User;
import tech.thapelomalifi.jwtpractice.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public User signUpUser(User user) {
        Optional.ofNullable(userRepository.findByEmail(user.getEmail())).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.CONFLICT)
        );

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public TokenResponseDTO loginUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.FORBIDDEN, "wrong email or password")
        );

        String token = jwtService.generateToken(new MyUserDetails(user));
        TokenResponseDTO responseToken = new TokenResponseDTO();
        responseToken.setToken(token);
        return responseToken;
    }
}
