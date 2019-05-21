package com.example.ilham.obatherbal.databaseJava.tacit;

public class tacitModel {
    private String _id,datePublishTacit,titleTacit,contentTacit,fileTacit,uploaderTacit;

    public tacitModel(String _id, String datePublishTacit, String titleTacit, String contentTacit,  String fileTacit, String uploaderTacit) {
        this._id = _id;
        this.datePublishTacit = datePublishTacit;
        this.titleTacit = titleTacit;
        this.contentTacit = contentTacit;
        this.fileTacit = fileTacit;
        this.uploaderTacit = uploaderTacit;
    }

    public String get_id() {
        return _id;
    }

    public String getDatePublishTacit() {
        return datePublishTacit;
    }

    public String getTitleTacit() {
        return titleTacit;
    }

    public String getContentTacit() {
        return contentTacit;
    }


    public String getFileTacit() {
        return fileTacit;
    }

    public String getUploaderTacit() {
        return uploaderTacit;
    }
}
