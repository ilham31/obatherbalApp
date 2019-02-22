package com.example.ilham.obatherbal.analysisJava.prediction.chooseMethod;

public class methodModel {
    String idCategories,categories;

    public methodModel(String idCategories, String categories) {
        this.idCategories = idCategories;
        this.categories = categories;
    }

    public String getIdCategories() {
        return idCategories;
    }

    public String getCategories() {
        return categories;
    }

    public String toString()
    {
        return categories;
    }
}
