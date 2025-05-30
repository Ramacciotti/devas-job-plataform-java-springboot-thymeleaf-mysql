package com.ramacciotti.devas.repository;

import com.ramacciotti.devas.enums.Level;
import com.ramacciotti.devas.enums.Objective;
import com.ramacciotti.devas.enums.Preference;
import com.ramacciotti.devas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "user")
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN u.about a " +
            "LEFT JOIN u.social s " +
            "LEFT JOIN u.job j " +
            "WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.city) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(s.linkedin) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(s.github) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(j.position) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.technology) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(j.level) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(j.preference) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(j.objective) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> searchUsers(@Param("search") String search);

}
