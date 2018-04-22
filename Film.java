package com.example.edy.kino;

public class Film {
    private long ID;
    private String nazov;
    private int dlzka;
    private int rok;
    private String popis;
    private String novaFukncia;

    public Film(long ID,String nazov,int dlzka,int rok, String popis) {
        this.ID=ID;
        this.nazov=nazov;
        this.dlzka=dlzka;
        this.rok=rok;
        this.popis=popis;
    }

    public void NovaFunkciaNaVyskusanieGitHubu() {
    public String najkrajsiaFunkciaAkuSomKedyVidel;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public int getDlzka() {
        return dlzka;
    }

    public void setDlzka(int dlzka) {
        this.dlzka = dlzka;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

}
