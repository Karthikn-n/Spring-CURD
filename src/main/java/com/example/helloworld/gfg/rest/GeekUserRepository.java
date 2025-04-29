package com.example.helloworld.gfg.rest;

import org.springframework.data.repository.CrudRepository;

public interface GeekUserRepository extends CrudRepository<GeekUserRecord, String> {
}