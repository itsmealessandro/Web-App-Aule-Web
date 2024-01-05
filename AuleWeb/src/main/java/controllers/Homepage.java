package controllers;

import data.dao.AuleWebDataLayer;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Homepage extends AuleWebBaseController {

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
            request.setAttribute("dipartimento1", dataLayer.getDipartimentoDAO().getDipartimento(1));
            request.setAttribute("dipartimento2", dataLayer.getDipartimentoDAO().getDipartimento(2));
            request.setAttribute("dipartimento3", dataLayer.getDipartimentoDAO().getDipartimento(3));
            
            //NO VABBE INSANE BRO GETKEY RISOLVE LE COSE

            res.activate("homepage.ftl.html", request, response);
        } catch (DataException ex) {
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
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
