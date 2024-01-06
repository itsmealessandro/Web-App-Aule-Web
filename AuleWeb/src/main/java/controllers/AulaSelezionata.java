package controllers;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Dipartimento;
import data.domain.Evento;

public class AulaSelezionata extends AuleWebBaseController {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  // Se non sono stati passati parametri si chiama questo metodo che carica un
  // dipartimento con un'aula
  private void action_default(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().getAula(3);
      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimento(1);
      List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByAula(aula);

      request.setAttribute("aula", aula);
      request.setAttribute("dipartimento", dipartimento);
      request.setAttribute("eventi", listaEventi);
      res.activate("aulaSelezionata.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  // Usa l'ID preso dalla GET per caricare un'aula di quel dipartimento
  private void action_only_dipartimento(HttpServletRequest request, HttpServletResponse response, int dipartimento_key)
      throws IOException, ServletException, TemplateManagerException {
    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().getAula(3);
      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimento(dipartimento_key);
      List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByAula(aula);

      request.setAttribute("aula", aula);
      request.setAttribute("eventi", listaEventi);
      request.setAttribute("dipartimento", dipartimento);
      res.activate("aulaSelezionata.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  // Prende i parametri dalla Get e chiama i metodi corrispettivi
  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    request.setAttribute("page_title", "Aula Selezionata");
    try {
      int dipartimento_key;
      if (request.getParameter("d") != null) {
        dipartimento_key = SecurityHelpers.checkNumeric(request.getParameter("d"));
        action_only_dipartimento(request, response, dipartimento_key);
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
