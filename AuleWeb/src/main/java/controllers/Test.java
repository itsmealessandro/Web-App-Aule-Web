package controllers;

import data.dao.AuleWebDataLayer;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Test extends AuleWebBaseController {

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

            AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

            request.setAttribute("page_title", "Test");
            request.setAttribute("dipartimento", dataLayer.getDipartimentoDAO().getDipartimento(1));
            request.setAttribute("amministratore", dataLayer.getAmministratoreDAO().getAmministratore(1));
            request.setAttribute("attrezzatura", dataLayer.getAttrezzaturaDAO().getAttrezzatura(1));
            request.setAttribute("aula", dataLayer.getAulaDAO().getAula(1));
            request.setAttribute("evento", dataLayer.getEventoDAO().getEventoByID(1));
            res.activate("testDB.ftl.html", request, response);

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
