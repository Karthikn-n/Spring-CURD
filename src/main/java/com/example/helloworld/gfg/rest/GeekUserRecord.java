package com.example.helloworld.gfg.rest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GeekUserRecord {
    @Id // For Primary key
    private int id;
    private String name;
    private String email;
    private String gender;
    private int numberOfPosts;
}
