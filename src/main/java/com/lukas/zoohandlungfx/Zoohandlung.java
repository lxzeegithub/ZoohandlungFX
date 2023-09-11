package com.lukas.zoohandlungfx;

import java.util.Arrays;

public class Zoohandlung {

    private boolean offen = false;
    private String name;
    private Tier[] tiere = new Tier[0];
    private Pfleger[] pfleger = new Pfleger[0];

    public Zoohandlung(String name) {
        this.name = name;
    }

    public void neuerPfleger(String name, String geschlecht, int alter, int gehalt) {
        pfleger = Arrays.copyOf(pfleger, pfleger.length+1);
        pfleger[pfleger.length-1] = new Pfleger(name, geschlecht, alter, gehalt);
    }

    public void neuesTier(String name, String rasse, int alter, int preis, String tierart) {
        switch (tierart) {
            case "Hund" -> addTier(new Hund(name, rasse, alter, preis));
            case "Katze" -> addTier(new Katze(name, rasse, alter, preis));
            case "Vogel" -> addTier(new Vogel(name, rasse, alter, preis));
            case "Hamster" -> addTier(new Hamster(name, rasse, alter, preis));
        }
    }

    public void tierEntfernen() {

    }

    public void pflegerEntfernen() {

    }

    private void addTier(Tier tier) {
        tiere = Arrays.copyOf(tiere, tiere.length+1);
        tiere[tiere.length-1] = tier;
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
