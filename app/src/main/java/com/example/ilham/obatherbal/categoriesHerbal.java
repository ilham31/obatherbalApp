package com.example.ilham.obatherbal;

public class categoriesHerbal {
    String idCategories,categories;

    public categoriesHerbal(String idCategories, String categories) {
        this.idCategories = idCategories;
        this.categories = categories;
    }

    public String getIdCategories() {
        return idCategories;
    }

    public String getCategories() {
        return categories;
    }
    public String toString()
    {
        return( categories  );
    }
}
