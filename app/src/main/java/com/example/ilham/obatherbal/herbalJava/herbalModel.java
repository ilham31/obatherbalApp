package com.example.ilham.obatherbal.herbalJava;

public class herbalModel {

    private String nama,khasiat,tipe,id;

    public herbalModel(String nama, String khasiat,String tipe,String id) {
        this.id=id;
        this.nama = nama;
        this.khasiat = khasiat;
        this.tipe = tipe;

    }

    public String getId(){return  id;}

    public String getTipe() {
        return tipe;
    }

    public String getNama() {
        return nama;
    }

    public String getKhasiat() {
        return khasiat;
    }
}
