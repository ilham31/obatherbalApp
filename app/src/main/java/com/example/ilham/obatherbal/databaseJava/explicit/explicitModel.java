package com.example.ilham.obatherbal.databaseJava.explicit;

public class explicitModel {
    private String _id,firstname,lastname,title,datePublish,citation,language,abstractExplicit,descriptionExplicit,doc;

    public explicitModel(String _id, String firstname, String lastname, String title, String datePublish, String citation, String language, String abstractExplicit, String descriptionExplicit, String doc) {
        this._id = _id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.datePublish = datePublish;
        this.citation = citation;
        this.language = language;
        this.abstractExplicit = abstractExplicit;
        this.descriptionExplicit = descriptionExplicit;
        this.doc = doc;
    }

    public String get_id() {
        return _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTitle() {
        return title;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public String getCitation() {
        return citation;
    }

    public String getLanguage() {
        return language;
    }

    public String getAbstractExplicit() {
        return abstractExplicit;
    }

    public String getDescriptionExplicit() {
        return descriptionExplicit;
    }

    public String getDoc() {
        return doc;
    }
}
