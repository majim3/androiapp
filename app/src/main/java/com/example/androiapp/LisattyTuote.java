package com.example.androiapp;

public class LisattyTuote {
    private double kofeiini;
    private double hinta;
    private String pvm;
    private String klo;

    /**
     * @Author Leo
     *Tässä on konstruktori lisättyihintuotteille muuttujille
     */

    public LisattyTuote(double kofeiini, double hinta, String pvm, String klo){
        this.kofeiini = kofeiini;
        this.hinta = hinta;
        this.pvm = pvm;
        this.klo = klo;
    }

    /**
     *
     * @Author Leo
     *Alhalla palautat muuttujille arvon
     *
    */
    @Override
    public String toString(){

        return kofeiini + " mg, " + hinta + " €, " + pvm;
    }

    public double getKofeiini(){
        return kofeiini;
    }
    public double getHinta() {
        return hinta;
    }
    public String getPvm(){
        return pvm;
    }
    public String getKlo(){
        return klo;
    }
}
