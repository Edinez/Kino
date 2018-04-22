package com.example.edy.kino;

public class Sala {
    private long ID;
    private String cislo;
    private int kapacita;

    public Sala(long ID,String cislo, int kapacita) {
        this.ID=ID;
        this.cislo=cislo;
        this.kapacita=kapacita;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getCislo() {
        return cislo;
    }

    public void setCislo(String cislo) {
        this.cislo = cislo;
    }

    public int getKapacita() {
        return kapacita;
    }

    public void setKapacita(int kapacita) {
        this.kapacita = kapacita;
    }
}
