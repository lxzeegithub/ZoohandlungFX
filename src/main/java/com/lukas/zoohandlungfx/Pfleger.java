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

    public void waschen(Tier tier) {
        System.out.println("Das Tier " + tier.getName() + " wurde gewaschen");
        tier.gibLaut();
    }

    public void streicheln(Tier tier) {
        System.out.println("Das Tier " + tier.getName() + " wurde gestreichelt");
        tier.gibLaut();
    }

    public String getName() {
        return name;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public int getAlter() {
        return alter;
    }

    public int getGehalt() {
        return gehalt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGehalt(int gehalt) {
        this.gehalt = gehalt;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }
}
