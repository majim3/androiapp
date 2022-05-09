package com.example.androiapp;

import java.util.HashMap;
import java.util.Objects;

public class TallennettuTuote {
    private String nimi;
    private double kofeiini;
    private double hinta;

    /**
     * @Author Leo
     *Alhaalla konstruktori Tallennettu tuotteille muuttujille
     * @param nimi palauttaa nimen
     * @param kofeiini palauttaaa kofeiini maaran
     * @param hinta palauttaa hinnan
     */

    public TallennettuTuote(String nimi, double kofeiini, double hinta){
        this.nimi = nimi;
        this.kofeiini = kofeiini;
        this.hinta = hinta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TallennettuTuote that = (TallennettuTuote) o;
        return nimi.equals(that.nimi);
    }

    /**
     * @author Leo
     * palauttaa arvot muuttujille
     * @return
     */

    @Override
    public int hashCode() {
        return Objects.hash(nimi);
    }

    public String toString(){
        return nimi;
    }

    public String getNimi(){
        return nimi;
    }

    public double getKofeiini(){
        return kofeiini;
    }

    public double getHinta(){
        return hinta;
    }
}
