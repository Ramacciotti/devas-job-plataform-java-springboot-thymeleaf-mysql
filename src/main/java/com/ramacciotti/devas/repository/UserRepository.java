package com.ramacciotti.devas.repository;

import com.ramacciotti.devas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "user")
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
