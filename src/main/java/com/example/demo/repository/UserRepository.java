package com.example.demo.repository;

import com.example.demo.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNameAndAndPassword(String name, String pwd);

}
