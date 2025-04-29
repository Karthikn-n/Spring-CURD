package com.example.helloworld.impl;

import com.example.helloworld.interfaces.Sim;

public class Airtel implements Sim {
    @Override
    public void calling() {
        System.out.println("Airtel sim calling");
    }

    @Override
    public void data() {
        System.out.println("Airtel data");
    }
}
