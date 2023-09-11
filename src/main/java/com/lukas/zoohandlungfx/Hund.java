package com.lukas.zoohandlungfx;

public class Hund extends Tier {
    public Hund(String name, String rasse, int alter, int preis) {
        super(name, rasse, alter, preis);
    }

    @Override
    public void gibLaut() {
        System.out.println("Wau Wau");
    }

    public void platz() {
        System.out.println("Hund hat Platz gemacht");
    }
}
