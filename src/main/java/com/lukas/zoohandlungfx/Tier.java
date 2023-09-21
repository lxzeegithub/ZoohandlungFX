package com.lukas.zoohandlungfx;

public abstract class Tier {

    private String name, rasse;
    private int alter, preis;

    public Tier(String name, String rasse, int alter, int preis) {
        this.name = name;
        this.rasse = rasse;
        this.alter = alter;
        this.preis = preis;
    }

    public abstract void gibLaut();
    public abstract String getTierart();

    public void seiWuetend() {
        gibLaut();
    }

    public String getName() {
        return name;
    }

    public String getRasse() {
        return rasse;
    }

    public int getAlter() {
        return alter;
    }

    public int getPreis() {
        return preis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRasse(String rase) {
        this.rasse = rasse;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }
}
