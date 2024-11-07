package vn.hoidanit.jobhunter.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.hoidanit.jobhunter.domain.User;

import java.util.Optional;

@Registered
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

}
