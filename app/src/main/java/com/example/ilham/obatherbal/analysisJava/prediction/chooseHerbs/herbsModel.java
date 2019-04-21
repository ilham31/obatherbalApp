package com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs;

public class herbsModel {
    private boolean isSelected;
    private String idHerbs,nameHerbs,idPlant;

    public herbsModel(String idHerbs, String nameHerbs, String idPlant) {
        this.idHerbs = idHerbs;
        this.nameHerbs = nameHerbs;
        this.idPlant = idPlant;
    }

    public String getIdPlant() {
        return idPlant;
    }

    public String getIdHerbs() {
        return idHerbs;
    }

    public String getNameHerbs() {
        return nameHerbs;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
