package com.example.aa.systemymobilne.model;

/**
 * Created by aa on 22.01.2018.
 */

public class PytaniaGra {


    private long id;

    private String pytanie;

    private String odpowiedz;

    private String kategoria;

//    private String odpowiedz;

    public PytaniaGra(long id, String pytanie, String  odpowiedz , String kategoria) {
        this.id = id;
        this.pytanie = pytanie;
        this.odpowiedz = odpowiedz;
        this.kategoria = kategoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPytanie() {
        return pytanie;
    }

    public void setPytanie(String pytanie) {
        this.pytanie = pytanie;
    }

//    public String getOdpowiedz() {
//        return odpowiedz;
//    }
//
//    public void setOdpowiedz(String odpowiedz) {
//        this.odpowiedz = odpowiedz;
//    }
//
    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }




    public String  getOdpowiedz() {
        return odpowiedz;
    }

    public void setCompleted(String  completed) {
        this.odpowiedz = completed;
    }
}
