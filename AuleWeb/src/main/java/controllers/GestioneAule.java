
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
import data.domain.Aula;

public class GestioneAule extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      List<Aula> listaAule = dataLayer.getAulaDAO().getAllAule();
      request.setAttribute("aule", listaAule);

      res.activate("adminGestioneAule.ftl.html", request, response);
    } catch (DataException ex) {
      handleError(ex.getMessage(), request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    request.setAttribute("page_title", "Gestione Aule");
    System.out.println("__________________________");
    try {
      action_default(request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }

}
