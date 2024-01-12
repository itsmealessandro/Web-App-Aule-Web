
package controllers;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AuleWebDataLayer;
import data.domain.Dipartimento;

public class GestioneAule extends AuleWebBaseController {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  private void action_default(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

    res.activate("adminGestioneAule.ftl.html", request, response);

  }

  private void action_update(HttpServletRequest request, HttpServletResponse response, String parameter) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  // Prende i parametri dalla Get e chiama i metodi corrispettivi
  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    request.setAttribute("page_title", "Gestione Aule");
    if (request.getParameter("a") != null) {
      action_update(request, response, request.getParameter("dipKey"));
    } else {
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
