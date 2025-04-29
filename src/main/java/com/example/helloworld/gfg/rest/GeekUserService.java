package com.example.helloworld.gfg.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeekUserService {

    @Autowired
    private GeekUserRepository geekUserRepository;

    // for getting all the geekusers, we need to write this
    // method and output is GeekUserRecord as a list,
    public List<GeekUserRecord> getAllGeekUsers() {
        List<GeekUserRecord> geekUserRecords = new ArrayList<>();
        /// Java 8 feature to use the lambda representation like (element) -> geekUserRecords.add(element)
        /// this is called method reference it actually to the same this as lambda
        geekUserRepository.findAll().forEach(geekUserRecords::add);
        return geekUserRecords;
    }

    // While adding a geekuser, we need to save that
    public void addGeekUser(GeekUserRecord userRecord)
    {
        geekUserRepository.save(userRecord);
    }
}