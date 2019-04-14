package com.example.ilham.obatherbal.analysisJava.ethnics;

public class ethnicModel {
    private String idEthnic,ethnicName,provinceId;

    public ethnicModel(String idEthnic, String ethnicName, String provinceId) {
        this.idEthnic = idEthnic;
        this.ethnicName = ethnicName;
        this.provinceId = provinceId;
    }

    public String getIdEthnic() {
        return idEthnic;
    }

    public String getEthnicName() {
        return ethnicName;
    }

    public String getProvinceId() {
        return provinceId;
    }
}
