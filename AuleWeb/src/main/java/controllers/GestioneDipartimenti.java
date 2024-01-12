package controllers;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AuleWebDataLayer;
import data.domain.Dipartimento;

public class GestioneDipartimenti extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      List<Dipartimento> listaDipartimenti = dataLayer.getDipartimentoDAO().getAllDipartimenti();

      request.setAttribute("dipartimenti", listaDipartimenti);
      res.activate("adminGestioneDip.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_update(HttpServletRequest request, HttpServletResponse response, String nome,
      String descrizione) {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      res.activate("operazioneEseguita.ftl.html", request, response);
    } catch (TemplateManagerException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  // Prende i parametri dalla Get e chiama i metodi corrispettivi
  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    if (request.getParameter("nome") != null && request.getParameter("descr") != null) {
      action_update(request, response, request.getParameter("nome"), request.getParameter("descr"));
    } else {
      request.setAttribute("page_title", "Gestione dipartimenti");
      try {
        action_default(request, response);
      } catch (NumberFormatException ex) {
        handleError("Invalid number submitted", request, response);
      } catch (IOException | TemplateManagerException ex) {
        handleError(ex, request, response);
      }
    }
  }

}
