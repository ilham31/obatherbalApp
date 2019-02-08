package com.example.ilham.obatherbal.herbalJava;

public class kampoModel {
    private String nama,khasiat,tipe,id,thumbnail;

    public kampoModel(String nama, String khasiat,String tipe,String id,String thumbnail) {
        this.id=id;
        this.nama = nama;
        this.khasiat = khasiat;
        this.tipe = tipe;
        this.thumbnail=thumbnail;

    }

    public String getIdKampo(){return  id;}

    public String getTipeKampo() {
        return tipe;
    }

    public String getNamaKampo() {
        return nama;
    }

    public String getKhasiatKampo() {
        return khasiat;
    }

    public String getThumbnailKampo() {
        return thumbnail;
    }
}
