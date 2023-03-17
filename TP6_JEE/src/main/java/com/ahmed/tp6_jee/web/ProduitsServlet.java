package com.ahmed.tp6_jee.web;

import com.ahmed.tp6_jee.dao.CategorieDaoImpl;
import com.ahmed.tp6_jee.dao.ICategorieDao;
import com.ahmed.tp6_jee.dao.ProduitDao;
import com.ahmed.tp6_jee.dao.ProduitDaoImpl;
import com.ahmed.tp6_jee.entities.Categorie;
import com.ahmed.tp6_jee.entities.Produits;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "produitsServlet", urlPatterns = {"/produits", "*.do"})
public class ProduitsServlet extends HttpServlet {
    ProduitDao metier;
    ICategorieDao metierCat;
    @Override
    public void init() throws ServletException {
        metier = new ProduitDaoImpl();
        metierCat = new CategorieDaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path=request.getServletPath();
        if (path.equals("/index.do"))
        {
            request.getRequestDispatcher("produits.jsp").forward(request,response);
        }
        else if (path.equals("/chercher.do"))
        {
            String motCle=request.getParameter("motCle");
            ProduitsModele model= new ProduitsModele();
            model.setMotCle(motCle);
            List<Produits> prods = metier.produitsParMC(motCle);
            model.setProduits(prods);
            request.setAttribute("model", model);
            request.getRequestDispatcher("produits.jsp").forward(request,response);
        }
        else if (path.equals("/saisie.do")) {
            List<Categorie> cats = metierCat.getAllCategories();
            CategorieModel model= new CategorieModel();
            model.setCategories(cats);
            request.setAttribute("catModel", model);

            request.getRequestDispatcher("saisieProduit.jsp").forward(request,response);

        }
        else if (path.equals("/save.do") && request.getMethod().equals("POST")) {
            String nom=request.getParameter("nom");
            Long categorieId=Long.parseLong(request.getParameter("categorie"));
            double prix = Double.parseDouble(request.getParameter("prix"));
            Categorie cat = metierCat.getCategorie(categorieId);
            Produits p = metier.save(new Produits(nom,prix,cat));
            request.setAttribute("produit", p);
            response.sendRedirect("chercher.do?motCle=");
        }
        else if (path.equals("/supprimer.do")) {
            int idProduit = Integer.parseInt(request.getParameter("id"));
            metier.deleteProduit(idProduit);
            response.sendRedirect("chercher.do?motCle=");

        } else if (path.equals("/editer.do")) {
            int idProduit = Integer.parseInt(request.getParameter("id"));
            Produits p = metier.getProduit(idProduit);
            request.setAttribute("produit", p);
            List<Categorie> cats = metierCat.getAllCategories();
            CategorieModel model= new CategorieModel();
            model.setCategories(cats);
            request.setAttribute("catModel", model);
            request.getRequestDispatcher("editerProduit.jsp").forward(request,response);

        } else if (path.equals("/update.do") && request.getMethod().equals("POST")) {
            int idProduit = Integer.parseInt(request.getParameter("id"));
            String nom = request.getParameter("nom");
            double prix = Double.parseDouble(request.getParameter("prix"));
            Long categorieId=Long.parseLong(request.getParameter("categorie"));
            Produits p = new Produits();
            p.setIdProduit(idProduit);
            p.setNomProduit(nom);
            p.setPrix(prix);
            Categorie cat = metierCat.getCategorie(categorieId);
            p.setCategorie(cat);
            metier.updateProduit(p);
            request.setAttribute("produit", p);
            //request.getRequestDispatcher("confirmation.jsp").forward(request,response);
            response.sendRedirect("chercher.do?motCle=");

        }
        else {
            response.sendError(response.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}