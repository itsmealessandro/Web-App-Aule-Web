package controllers;

import data.dao.AuleWebDataLayer;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Shomepage extends AuleWebBaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());

            // Log di debug
            System.out.println("Checking datalayer attribute...");

            AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

            if (dataLayer != null) {
                System.out.println("datalayer attribute is not null");

                request.setAttribute("page_title", "Dipartimenti");
                //request.setAttribute("dipartimento", dataLayer.getDipartimentoDAO().getDipartimento(1));
                request.setAttribute("dipartimento", dataLayer.getAmministratoreDAO().getAmministratore(1));
                res.activate("testDB.ftl.html", request, response);
            } else {
                System.out.println("datalayer attribute is null");
                // Gestisci il caso in cui l'attributo "datalayer" è null
                handleError("Data layer is null", request, response);
            }

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }
    

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);

        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

}
