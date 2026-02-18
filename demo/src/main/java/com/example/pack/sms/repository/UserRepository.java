package com.example.pack.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pack.sms.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
