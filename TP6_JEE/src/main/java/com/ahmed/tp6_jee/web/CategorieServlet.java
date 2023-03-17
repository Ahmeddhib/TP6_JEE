package com.ahmed.tp6_jee.web;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ahmed.tp6_jee.dao.CategorieDaoImpl;
import com.ahmed.tp6_jee.dao.ICategorieDao;
import com.ahmed.tp6_jee.entities.Categorie;
@WebServlet (name="catServ",urlPatterns= {"/categories","/saisieCategorie",
        "/saveCategorie","/supprimerCat","/editerCat","/updateCat"})

public class CategorieServlet extends HttpServlet {
    ICategorieDao dao;
    @Override
    public void init() throws ServletException {
        dao = new CategorieDaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String path=request.getServletPath();
        System.out.println("PATH "+path);
        if (path.equals("/categories"))
        {
            CategorieModel model= new CategorieModel();
            List<Categorie> cats = dao.getAllCategories();
            model.setCategories(cats);
            request.setAttribute("model", model);
            request.getRequestDispatcher("categories.jsp").forward(request,response);
        }
        else if (path.equals("/saisieCategorie") )
        {
            request.getRequestDispatcher("saisieCategorie.jsp").forward(request,response
            );
        }
        else if (path.equals("/saveCategorie") &&

                request.getMethod().equals("POST"))

        {
            Date dateCat= new Date();
            String nom=request.getParameter("nom");
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new

                    SimpleDateFormat(pattern);
            try {
                dateCat =

                        simpleDateFormat.parse(request.getParameter("dateCat"));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Categorie cat = dao.save(new Categorie(nom,dateCat));
            request.setAttribute("categorie", cat);
            response.sendRedirect("categories");
        }
        else if (path.equals("/supprimerCat"))
        {
            Long id= Long.parseLong(request.getParameter("id"));
            dao.deleteCategorie(id);
            response.sendRedirect("categories");
        }
        else if (path.equals("/editerCat") )
        {
            Long id= Long.parseLong(request.getParameter("id"));
            Categorie cat = dao.getCategorie(id);
            request.setAttribute("categorie", cat);
            request.getRequestDispatcher("editerCategorie.jsp").forward(request,response
            );
        }
        else if (path.equals("/updateCat") )
        {
            Date dateCat= new Date();
            Long id = Long.parseLong(request.getParameter("id"));
            String nom=request.getParameter("nom");
            Categorie cat = new Categorie();
            cat.setIdCat(id);
            cat.setNomCat(nom);
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new

                    SimpleDateFormat(pattern);
            try {

                dateCat =

                        simpleDateFormat.parse(request.getParameter("dateCreation"));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            cat.setDateCreation(dateCat);
            dao.updateCategorie(cat);
            response.sendRedirect("categories");
        }
        else
        {
            response.sendError(response.SC_NOT_FOUND);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws

            ServletException, IOException {
        doGet(request,response);
    }}
