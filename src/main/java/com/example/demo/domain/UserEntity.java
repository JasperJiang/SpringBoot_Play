package com.example.demo.domain;

import com.example.demo.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String name;

        private String password;

        private UserRoleEnum role;
}
