package tech.thapelomalifi.jwtpractice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.thapelomalifi.jwtpractice.model.MyUserDetails;
import tech.thapelomalifi.jwtpractice.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(
                userRepository.findByEmail(username).orElseThrow(
                        ()-> new UsernameNotFoundException("user not found")
                )
        );
    }
}
