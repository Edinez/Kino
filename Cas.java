package com.example.edy.kino;

public class Cas {
    private long ID_filmy;
    private long ID_sala;
    private String cas;
    private String den;
    private int cena;
    private int id;

    public Cas(int ID,String cas, String den, int cena, long ID_filmy, long ID_sala) {
        this.id=ID;
        this.cas = cas;
        this.den = den;
        this.cena = cena;
        this.ID_filmy = ID_filmy;
        this.ID_sala = ID_sala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getID_filmy() {
        return ID_filmy;
    }

    public void setID_filmy(long ID_filmy) {
        this.ID_filmy = ID_filmy;
    }

    public long getID_sala() {
        return ID_sala;
    }

    public void setID_sala(long ID_sala) {
        this.ID_sala = ID_sala;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
}
