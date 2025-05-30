package com.example.helloworld.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
