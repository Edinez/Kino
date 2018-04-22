package com.example.edy.kino;

/**
 * Created by Edy on 28.3.2018.
 */

public final class MyContract {

    public static class Cas {
        public static final String TABLE_NAME = "casy";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CAS = "cas";
        public static final String COLUMN_DEN = "den";
        public static final String COLUMN_CENA = "cena";
        public static final String COLUMN_ID_FILMY = "id_film";
        public static final String COLUMN_ID_SALY = "id_sala";
    }

    public static class Film {
        public static final String TABLE_NAME = "filmy";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAZOV = "nazov";
        public static final String COLUMN_DLZKA = "dlzka";
        public static final String COLUMN_ROK = "rok";
        public static final String COLUMN_POPIS = "popis";
    }

    public static class Sala {
        public static final String TABLE_NAME = "saly";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CISLO = "cislo";
        public static final String COLUMN_KAPACITA = "kapacita";
    }
}
