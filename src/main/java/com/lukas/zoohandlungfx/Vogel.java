package com.lukas.zoohandlungfx;

public class Vogel extends Tier {
    public Vogel(String name, String rasse, int alter, int preis) {
        super(name, rasse, alter, preis);
    }

    @Override
    public void gibLaut() {
        System.out.println("Vogelgeräusch");
    }

    public void fliegen() {
        System.out.println("Vogel ist geflogen");
    }
}
