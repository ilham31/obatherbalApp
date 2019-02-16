package com.example.ilham.obatherbal.analysisJava.prediction.chooseHerbs;

public class herbsModel {
    private boolean isSelected;
    private String idHerbs,nameHerbs;

    public herbsModel(String idHerbs, String nameHerbs) {
        this.idHerbs = idHerbs;
        this.nameHerbs = nameHerbs;
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
