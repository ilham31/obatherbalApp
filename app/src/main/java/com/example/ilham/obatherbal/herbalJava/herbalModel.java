package com.example.ilham.obatherbal.herbalJava;

import android.widget.ImageView;

public class herbalModel {

    private String nama,khasiat,tipe,id,thumbnail;

    public herbalModel(String nama, String khasiat,String tipe,String id,String thumbnail) {
        this.id=id;
        this.nama = nama;
        this.khasiat = khasiat;
        this.tipe = tipe;
        this.thumbnail=thumbnail;

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

    public String getThumbnail() {
        return thumbnail;
    }
}
