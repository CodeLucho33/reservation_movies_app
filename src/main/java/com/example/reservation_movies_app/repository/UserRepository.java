package com.example.reservation_movies_app.repository;

import com.example.reservation_movies_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String defaultEmail);
}
