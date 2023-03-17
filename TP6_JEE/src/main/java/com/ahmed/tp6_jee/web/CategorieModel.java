package com.ahmed.tp6_jee.web;

import com.ahmed.tp6_jee.entities.Categorie;

import java.util.ArrayList;
import java.util.List;

public class CategorieModel {
    List<Categorie> categories = new ArrayList<>();
    public List<Categorie> getCategories() {
        return categories;
    }
    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
}
