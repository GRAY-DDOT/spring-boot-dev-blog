package com.ddot.springbootdevblog.repository;

import com.ddot.springbootdevblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // 위 코드 실제 동작
    // FROM users
    // WHERE email = #{email}
}
