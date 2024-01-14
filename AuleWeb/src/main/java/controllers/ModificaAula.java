
package controllers;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AuleWebDataLayer;
import data.domain.Aula;

public class ModificaAula extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response, int a_key)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().getAulaByID(a_key);
      request.setAttribute("aula", aula);

      res.activate("adminModificaAula.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_update(HttpServletRequest request, HttpServletResponse response, int a_key, String nome,
      String descrizione)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().getAulaByID(a_key);
      request.setAttribute("aula", aula);

      res.activate("adminModificaDip.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    request.setAttribute("page_title", "Modifica Dipartimento");
    try {
      if (request.getParameter("a_key") != null &&
          request.getParameter("name") != null &&
          request.getParameter("descr") != null) {

        int a_key = SecurityHelpers.checkNumeric(request.getParameter("a_key"));
        String nome = request.getParameter("name");
        String descrizione = request.getParameter("descrizione");
        action_update(request, response, a_key, nome, descrizione);

      } else if (request.getParameter("a_key") != null) {
        int a_key = SecurityHelpers.checkNumeric(request.getParameter("a_key"));
        action_default(request, response, a_key);
      }
      // TODO Gestire caso senza parametri

    } catch (NumberFormatException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }

}
