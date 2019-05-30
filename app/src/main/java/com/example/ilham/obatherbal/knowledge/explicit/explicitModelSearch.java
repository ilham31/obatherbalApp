package com.example.ilham.obatherbal.knowledge.explicit;

public class explicitModelSearch {
    private String _id,title,description;

    public explicitModelSearch(String _id, String title, String description) {
        this._id = _id;
        this.title = title;
        this.description = description;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
