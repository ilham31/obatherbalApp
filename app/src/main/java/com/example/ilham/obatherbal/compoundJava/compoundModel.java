package com.example.ilham.obatherbal.compoundJava;

public class compoundModel {

    private String id,nama,partOfplant,plantspecies,molecular_formula,ref;

    public compoundModel(String id, String nama, String partOfplant, String plantspecies, String molecular_formula, String ref) {
        this.id = id;
        this.nama = nama;
        this.partOfplant = partOfplant;
        this.plantspecies = plantspecies;
        this.molecular_formula = molecular_formula;
        this.ref = ref;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPartOfplant() {
        return partOfplant;
    }

    public String getPlantspecies() {
        return plantspecies;
    }

    public String getMolecular_formula() {
        return molecular_formula;
    }

    public String getRef() {
        return ref;
    }
}
