package com.lukas.zoohandlungfx;

public class Katze extends Tier {
    public Katze(String name, String rasse, int alter, int preis) {
        super(name, rasse, alter, preis);
    }

    @Override
    public void gibLaut() {
        System.out.println("Miau Miau");
    }

    public void fauchen() {
        System.out.println("Katze hat gefaucht");
    }
}
