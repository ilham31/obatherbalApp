package com.example.ilham.obatherbal.crudeJava;

public class crudeModel {

    private String id,nama,refimgplant;

    public crudeModel(String id, String nama, String refimgplant) {
        this.id = id;
        this.nama = nama;
        this.refimgplant = refimgplant;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getRefimgplant(){return refimgplant;}
}
