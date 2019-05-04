package com.example.ilham.obatherbal.analysisJava.ethnics;

public class detailDiseaseModel {
    private String tanaman,spesies,bagian;

    public detailDiseaseModel(String tanaman, String spesies, String bagian) {
        this.tanaman = tanaman;
        this.spesies = spesies;
        this.bagian = bagian;
    }

    public String getTanaman() {
        return tanaman;
    }

    public String getSpesies() {
        return spesies;
    }

    public String getBagian() {
        return bagian;
    }
}
