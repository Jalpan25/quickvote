package com.example.QuickVote.repository;

//public interface UserRepository {
//}
//package com.example.demo.repository;

//import com.example.demo.entities.User;
import com.example.QuickVote.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
