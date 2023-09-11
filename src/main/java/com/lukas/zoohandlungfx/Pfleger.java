package com.lukas.zoohandlungfx;

public class Pfleger {

    private String name, geschlecht;
    private int alter, gehalt;

    public Pfleger (String name, String geschlecht, int alter, int gehalt) {
        this.name = name;
        this.geschlecht = geschlecht;
        this.alter = alter;
        this.gehalt = gehalt;
    }

    public void fuettern(Tier tier) {
        System.out.println("Das Tier " + tier.getName() + " wurde gefüttert");
        tier.gibLaut();
    }

    public String getName() {
        return name;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public int alter() {
        return alter;
    }

    public int gehalt() {
        return gehalt;
    }
}
