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
import data.domain.Dipartimento;

public class SceltaCriterio extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response, int dip_key)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimento(dip_key);

      request.setAttribute("dipartimento", dipartimento);
      res.activate("sceltaCriterio.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    request.setAttribute("page_title", "Gestione dipartimenti");

    int dip_key = 0;
    if (request.getParameter("dip_key") != null) {
      dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
    } else {
      handleError("Nessun dipartimento Selezionato", request, response);
    }
    try {
      action_default(request, response, dip_key);
    } catch (NumberFormatException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }

}
