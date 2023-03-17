package com.ahmed.tp6_jee.dao;

import com.ahmed.tp6_jee.entities.Categorie;

import java.util.List;

public interface ICategorieDao {
    public Categorie save(Categorie cat);
    public Categorie getCategorie(Long id);
    public Categorie updateCategorie(Categorie cat);
    public void deleteCategorie(Long id);
    public List<Categorie> getAllCategories();
}
