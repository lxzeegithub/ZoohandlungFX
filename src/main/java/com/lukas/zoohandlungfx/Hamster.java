package com.lukas.zoohandlungfx;

public class Hamster extends Tier {
    public Hamster(String name, String rasse, int alter, int preis) {
        super(name, rasse, alter, preis);
    }

    @Override
    public void gibLaut() {
        System.out.println("Hamstergeräusch");
    }

    public void rennen() {
        System.out.println("Hamster rennt los");
    }

    public String getTierart() {
        return "Hamster";
    }
}
