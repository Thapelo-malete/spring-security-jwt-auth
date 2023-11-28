package tech.thapelomalifi.jwtpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.thapelomalifi.jwtpractice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
