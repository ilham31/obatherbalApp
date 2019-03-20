package com.example.ilham.obatherbal.crudeJava;

public class detailCrudeModel {
    private String sname,name_en,name_loc,gnameCrude,positionCrude,effect,ref;

    public detailCrudeModel(String sname, String name_en, String name_loc, String gnameCrude, String positionCrude, String effect, String ref) {
        this.sname = sname;
        this.name_en = name_en;
        this.name_loc = name_loc;
        this.gnameCrude = gnameCrude;
        this.positionCrude = positionCrude;
        this.effect = effect;
        this.ref = ref;
    }

    public String getSname() {
        return sname;
    }

    public String getName_en() {
        return name_en;
    }

    public String getName_loc() {
        return name_loc;
    }

    public String getGnameCrude() {
        return gnameCrude;
    }

    public String getPositionCrude() {
        return positionCrude;
    }

    public String getEffect() {
        return effect;
    }

    public String getRef() {
        return ref;
    }
}
