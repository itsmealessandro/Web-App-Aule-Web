package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Evento;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestioneEventi extends AuleWebBaseController{

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
            List<Aula> listaAule = dataLayer.getAulaDAO().getAllAule();
            Aula aula = listaAule.get(0);
            res.activate("gestioneEventi.ftl.html", request, response);
            List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByAula(aula);
            request.setAttribute("eventi", listaEventi);
            
            request.setAttribute("aula", aula);
        } catch (DataException ex) {
            Logger.getLogger(GestioneEventi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      // Usa l'ID preso dalla GET per caricare la lista di eventi di un'aula 
  private void action_filtro(HttpServletRequest request, HttpServletResponse response, int aula_key)
      throws IOException, ServletException, TemplateManagerException {
    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().getAulaByID(aula_key);
      List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByAula(aula);

      request.setAttribute("aula", aula);
      request.setAttribute("eventi", listaEventi);
      res.activate("gestioneEventi.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException {
    request.setAttribute("page_title", "Gestione Eventi");
    try {
      int aula_key;
      if (request.getParameter("d") != null) {
        aula_key = SecurityHelpers.checkNumeric(request.getParameter("d"));
        action_filtro(request, response, aula_key);
      } else {
        action_default(request, response);
      }
    } catch (NumberFormatException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }
}
