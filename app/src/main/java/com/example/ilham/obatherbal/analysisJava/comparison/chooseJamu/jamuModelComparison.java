package com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu;

import android.support.annotation.NonNull;

public class jamuModelComparison {
    private String id,nama;

    public jamuModelComparison(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    @NonNull
    @Override
    public String toString() {
        return nama;
    }
}
