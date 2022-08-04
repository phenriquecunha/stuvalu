package com.cunha.stuvalu.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private int stars;
    @Column
    private ArrayList<UUID> posts;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

}
