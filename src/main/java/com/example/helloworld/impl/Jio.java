package com.example.helloworld.impl;

import com.example.helloworld.interfaces.Sim;

public class Jio implements Sim {
    @Override
    public void calling() {
        System.out.println("Jio sim calling...");
    }

    @Override
    public void data() {
        System.out.println("Jio sim data");
    }
}
