package com.example.ilham.obatherbal.analysisJava.prediction.chooseCompound;

public class compoundPredictModel {
    private String idData,nameCompound,idCompound;

    public compoundPredictModel(String idData, String nameCompound, String idCompound) {
        this.idData = idData;
        this.nameCompound = nameCompound;
        this.idCompound = idCompound;
    }

    public String getIdData() {
        return idData;
    }

    public String getNameCompound() {
        return nameCompound;
    }

    public String getIdCompound() {
        return idCompound;
    }
}
