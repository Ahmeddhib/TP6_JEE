package com.ahmed.tp6_jee.dao;


import com.ahmed.tp6_jee.entities.Produits;
import com.ahmed.tp6_jee.utils.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ProduitDaoImpl implements ProduitDao {
    private EntityManager entityManager = JpaUtil.getEntityManager("default");

    @Override
    public Produits save(Produits produits) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(produits);
        entityTransaction.commit();
        return produits;
    }

    @Override
    public List<Produits> produitsParMC(String mc) {
        List<Produits> prods = entityManager.createQuery("select p from Produits p where p.nomProduit like :x")
                .setParameter("x", "%" + mc + "%")
                .getResultList();
        return prods;
    }

    @Override
    public Produits getProduit(int idProduit) {
        return entityManager.find(Produits.class, idProduit);
    }

    @Override
    public Produits updateProduit(Produits produits) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(produits);
        entityTransaction.commit();
        return produits;
    }

    @Override
    public void deleteProduit(int idProduit) {
        Produits produits = entityManager.find(Produits.class, idProduit);
        entityManager.getTransaction().begin();
        entityManager.remove(produits);
        entityManager.getTransaction().commit();
    }
}
