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

public class ModificaDipartimento extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response, int dip_key)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimento(dip_key);

      request.setAttribute("dipartimento", dipartimento);

      res.activate("adminModificaDip.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_update(HttpServletRequest request, HttpServletResponse response, int dip_key, String nome,
      String descrizione)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimento(dip_key);
      dipartimento.setNome(nome);
      dipartimento.setDescrizione(descrizione);

      dataLayer.getDipartimentoDAO().storeDipartimento(dipartimento);

      request.setAttribute("dipartimento", dipartimento);

      res.activate("operazioneEseguita.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    request.setAttribute("page_title", "Modifica Dipartimento");
    try {
      if (request.getParameter("dip_key") != null &&
          request.getParameter("name") != null &&
          request.getParameter("descr") != null) {

        int dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
        String nome = request.getParameter("name");
        String descrizione = request.getParameter("descr");
        action_update(request, response, dip_key, nome, descrizione);

      } else if (request.getParameter("dip_key") != null) {
        int dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
        action_default(request, response, dip_key);

      }
      // TODO Gestire caso senza parametri

    } catch (NumberFormatException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }

}
