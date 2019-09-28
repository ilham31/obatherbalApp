package com.example.ilham.obatherbal.analysisJava.ethnics;

public class diseaseModel {
    private String diseaseINA,diseaseENG,nameINA,species,family,sectionINA,sectionENG;

    public diseaseModel(String diseaseINA, String diseaseENG, String nameINA, String species, String family, String sectionINA, String sectionENG) {
        this.diseaseINA = diseaseINA;
        this.diseaseENG = diseaseENG;
        this.nameINA = nameINA;
        this.species = species;
        this.family = family;
        this.sectionINA = sectionINA;
        this.sectionENG = sectionENG;
    }

    public String getDiseaseINA() {
        return diseaseINA;
    }

    public String getDiseaseENG() {
        return diseaseENG;
    }

    public String getNameINA() {
        return nameINA;
    }

    public String getSpecies() {
        return species;
    }

    public String getFamily() {
        return family;
    }

    public String getSectionINA() {
        return sectionINA;
    }

    public String getSectionENG() {
        return sectionENG;
    }
}
