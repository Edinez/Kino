package com.example.edy.kino;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kino.db";

    public DBHelper(Context context) { // konštruktor databázy
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE_FILM = "CREATE TABLE " + MyContract.Film.TABLE_NAME + " ( "
                + MyContract.Film.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyContract.Film.COLUMN_NAZOV + " TEXT, "
                + MyContract.Film.COLUMN_DLZKA + " INTEGER, "
                + MyContract.Film.COLUMN_ROK + " INTEGER, "
                + MyContract.Film.COLUMN_POPIS + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FILM);
        Log.d("create", "vytvoril som");


        String SQL_CREATE_TABLE_SALA = "CREATE TABLE " + MyContract.Sala.TABLE_NAME + " ( "
                + MyContract.Sala.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyContract.Sala.COLUMN_CISLO + " INTEGER, "
                + MyContract.Sala.COLUMN_KAPACITA + " INTEGER)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SALA);
        Log.d("create", "vytvoril som");


        String SQL_CREATE_TABLE_CAS = "CREATE TABLE " + MyContract.Cas.TABLE_NAME + " ( "
                + MyContract.Cas.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyContract.Cas.COLUMN_CAS + " TEXT, "
                + MyContract.Cas.COLUMN_DEN + " TEXT, "
                + MyContract.Cas.COLUMN_CENA + " INTEGER, "
                + MyContract.Cas.COLUMN_ID_FILMY + " INTEGER , "
                + MyContract.Cas.COLUMN_ID_SALY + " INTEGER)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CAS);
        Log.d("create", "vytvoril som");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContract.Film.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContract.Sala.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContract.Cas.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addFilm(Film f) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Film.COLUMN_NAZOV, f.getNazov());
        values.put(MyContract.Film.COLUMN_DLZKA, f.getDlzka());
        values.put(MyContract.Film.COLUMN_ROK, f.getRok());
        values.put(MyContract.Film.COLUMN_POPIS, f.getPopis());

        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert(MyContract.Film.TABLE_NAME, null, values);
        db.close();
    }

    public void updateFilm(Film f) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Film.COLUMN_NAZOV, f.getNazov());
        values.put(MyContract.Film.COLUMN_DLZKA, f.getDlzka());
        values.put(MyContract.Film.COLUMN_ROK, f.getRok());
        values.put(MyContract.Film.COLUMN_POPIS, f.getPopis());
        SQLiteDatabase db = getWritableDatabase();
        db.update(
                MyContract.Film.TABLE_NAME, values, MyContract.Film.COLUMN_ID + "= ?", new String[]{"" + f.getID()});
        db.close();
    }

    public List<HashMap<String, String>> getFilmy() {
        SQLiteDatabase db = getWritableDatabase();
        List<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>();
        Cursor c = db.rawQuery("select * from " + MyContract.Film.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_ID)));
                map.put("nazov", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_NAZOV)));
                map.put("dlzka", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_DLZKA)));
                map.put("rok", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_ROK)));
                map.put("popis", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_POPIS)));
                zoznam.add(map);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return zoznam;
    }


    public int zistiPodlaNazvuFilm(String nazovFilmu) {
        SQLiteDatabase db = getWritableDatabase();
        String dotaz = "select _id from " + MyContract.Film.TABLE_NAME + " where " + MyContract.Film.COLUMN_NAZOV + " = '" + nazovFilmu + "'";
        Cursor c = db.rawQuery(dotaz, null);
        int idFilmu = 0;
        if (c.moveToFirst()) {
            idFilmu = c.getInt(c.getColumnIndex(MyContract.Film.COLUMN_ID));
        }
        db.close();
        return idFilmu;
    }

    public int zistiPodlaNazvuSalu(String nazovSaly) {
        SQLiteDatabase db = getWritableDatabase();
        String dotaz = "select _id from " + MyContract.Sala.TABLE_NAME + " where " + MyContract.Sala.COLUMN_CISLO + " = '" + nazovSaly + "'";
        Cursor c = db.rawQuery(dotaz, null);
        int idSaly = 0;
        if (c.moveToFirst()) {
            idSaly = c.getInt(c.getColumnIndex(MyContract.Sala.COLUMN_ID));
        }
        db.close();
        return idSaly;
    }

    public void deleteFilm(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MyContract.Film.TABLE_NAME, MyContract.Film.COLUMN_ID + "= ?", new String[]{"" + ID});
        db.close();
    }

    public void deleteUdalost(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MyContract.Cas.TABLE_NAME, MyContract.Cas.COLUMN_ID + "= ?", new String[]{"" + ID});
        Log.v("MainAct", db.delete(MyContract.Cas.TABLE_NAME, MyContract.Cas.COLUMN_ID + "= ?", new String[]{"" + ID}) + "");
        db.close();
    }

    public void addSalu(Sala s) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Sala.COLUMN_CISLO, s.getCislo());
        values.put(MyContract.Sala.COLUMN_KAPACITA, s.getKapacita());

        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert(MyContract.Sala.TABLE_NAME, null, values);
        db.close();
    }

    public void addCas(Cas c) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Cas.COLUMN_CAS, c.getCas());
        values.put(MyContract.Cas.COLUMN_DEN, c.getDen());
        values.put(MyContract.Cas.COLUMN_CENA, c.getCena());
        values.put(MyContract.Cas.COLUMN_ID_FILMY, c.getID_filmy());
        values.put(MyContract.Cas.COLUMN_ID_SALY, c.getID_sala());

        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert(MyContract.Cas.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteSalu(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MyContract.Sala.TABLE_NAME, MyContract.Sala.COLUMN_ID + "= ?", new String[]{"" + ID});
        db.close();
    }

    public Cursor VratKurzor() {
        SQLiteDatabase db = getWritableDatabase();
        String sstr = "select * from " + MyContract.Film.TABLE_NAME;
        Cursor c = db.rawQuery(sstr, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public Cursor VratKurzorSala() {
        SQLiteDatabase db = getWritableDatabase();
        String sstr = "select * from " + MyContract.Sala.TABLE_NAME;
        Cursor c = db.rawQuery(sstr, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public Cursor ArrayFilm() {
        SQLiteDatabase db = getWritableDatabase();
        String dotaz = "select * from " + MyContract.Film.TABLE_NAME + " inner join casy ON filmy._id=casy.id_film inner join saly ON saly._id=casy.id_sala";
        Cursor c = db.rawQuery(dotaz, null);
        if (c.moveToFirst()) {
            return c;

        }
        return null;
    }

    public Cursor VratKurzorCas() {
        SQLiteDatabase db = getWritableDatabase();
        String dotazCas = " select " + MyContract.Cas.TABLE_NAME+"."+MyContract.Cas.COLUMN_ID + ", " +
                MyContract.Film.COLUMN_NAZOV + ", " +
                MyContract.Sala.COLUMN_CISLO + ", " +
                   MyContract.Cas.COLUMN_DEN + ", " +
                   MyContract.Cas.COLUMN_CAS + ", " +
                MyContract.Cas.COLUMN_CENA + " from " + MyContract.Cas.TABLE_NAME + " inner join filmy ON filmy._id=casy.id_film inner join saly ON saly._id=casy.id_sala";
        Cursor c = db.rawQuery(dotazCas, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public List<HashMap<String, String>> getSalas() {
        SQLiteDatabase db = getWritableDatabase();
        List<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>();
        Cursor c = db.rawQuery("select * from " + MyContract.Sala.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", c.getString(c.getColumnIndex(MyContract.Sala.COLUMN_ID)));
                map.put("cislo", c.getString(c.getColumnIndex(MyContract.Sala.COLUMN_CISLO)));
                map.put("kapacita", c.getString(c.getColumnIndex(MyContract.Sala.COLUMN_KAPACITA)));
                zoznam.add(map);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return zoznam;
    }

    public List<HashMap<String, String>> getCas() {
        SQLiteDatabase db = getWritableDatabase();
        List<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>();
        Cursor c = db.rawQuery("select * from " + MyContract.Cas.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", c.getString(c.getColumnIndex(MyContract.Cas.COLUMN_ID)));
                map.put("cas", c.getString(c.getColumnIndex(MyContract.Cas.COLUMN_CAS)));
                map.put("den", c.getString(c.getColumnIndex(MyContract.Cas.COLUMN_DEN)));
                map.put("cena", c.getString(c.getColumnIndex(MyContract.Cas.COLUMN_CENA)));
                zoznam.add(map);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return zoznam;
    }

    public void updateSala(Sala s) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Sala.COLUMN_CISLO, s.getCislo());
        values.put(MyContract.Sala.COLUMN_KAPACITA, s.getKapacita());
        SQLiteDatabase db = getWritableDatabase();
        db.update(
                MyContract.Sala.TABLE_NAME, values, MyContract.Sala.COLUMN_ID + "= ?", new String[]{"" + s.getID()});
        db.close();
    }

    public void updateCas(Cas s) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Cas.COLUMN_CAS, s.getCas());
        values.put(MyContract.Cas.COLUMN_DEN, s.getDen());
        values.put(MyContract.Cas.COLUMN_CENA, s.getCena());
        values.put(MyContract.Cas.COLUMN_ID_FILMY, s.getID_filmy());
        values.put(MyContract.Cas.COLUMN_ID_SALY, s.getID_sala());
        SQLiteDatabase db = getWritableDatabase();
        db.update(
                MyContract.Cas.TABLE_NAME, values, MyContract.Cas.COLUMN_ID + "= ?", new String[]{"" + s.getId()});
        db.close();
    }


    public List<HashMap<String, String>> getFilmyInfo() {
        SQLiteDatabase db = getWritableDatabase();
        List<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>();
        Cursor c = db.rawQuery("select * from " + MyContract.Film.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_ID)));
                map.put("nazov", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_NAZOV)));
                map.put("dlzka", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_DLZKA)));
                map.put("rok", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_ROK)));
                map.put("popis", c.getString(c.getColumnIndex(MyContract.Film.COLUMN_POPIS)));
                zoznam.add(map);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return zoznam;
    }
}
