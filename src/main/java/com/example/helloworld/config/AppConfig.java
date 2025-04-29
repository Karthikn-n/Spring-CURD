package com.example.helloworld.config;

import com.example.helloworld.impl.Airtel;
import com.example.helloworld.impl.Jio;
import com.example.helloworld.interfaces.Sim;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Sim sim(){
        return new Airtel();
    }
    @Bean("jio")
    public Sim sim1(){
        return new Jio();
    }
}
