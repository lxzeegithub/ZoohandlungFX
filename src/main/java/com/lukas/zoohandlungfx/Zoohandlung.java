package com.lukas.zoohandlungfx;

public class Zoohandlung {

    private boolean offen = false;
    private String name;
    private Tier[] tiere = new Tier[0];
    private Pfleger[] pfleger = new Pfleger[0];

    public Zoohandlung(String name) {
        this.name = name;
    }

    public void neuerPfleger() {

    }

    public void neuesTier() {

    }

    public Tier[] getTiere() {
        return tiere;
    }

    public Pfleger[] getPfleger() {
        return pfleger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void oeffnen() {
        offen = true;
    }

    public void schliessen() {
        offen = false;
    }

    public boolean isOpen() {
        return offen;
    }
}
