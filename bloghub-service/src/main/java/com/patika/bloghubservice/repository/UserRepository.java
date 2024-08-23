package com.patika.bloghubservice.repository;

import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
