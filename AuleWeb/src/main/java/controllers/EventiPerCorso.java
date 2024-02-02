package controllers;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AuleWebDataLayer;
import data.domain.Corso;
import data.domain.Evento;

public class EventiPerCorso extends AuleWebBaseController {

  // Usa il paramentro date preso dalla GET per lanciare una vista con gli eventi
  // di quella settimana
  private void action_eventi_per_corso(HttpServletRequest request, HttpServletResponse response, int c_key,
      int dip_key, LocalDate data)
      throws IOException, ServletException, TemplateManagerException {
    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Corso corso = dataLayer.getCorsoDAO().getCorso(c_key);
      List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiSettimanaliByCorso(corso, data, dip_key);

      request.setAttribute("eventi", listaEventi);
      res.activate("eventiCorso.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  // Prende il parametri ID dipartimento e ID Corso
  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    request.setAttribute("page_title", "Eventi Per Giorno");
    try {
      int dip_key;
      int c_key;
      String data;

      if (request.getParameter("c_key") != null && request.getParameter("dip_key") != null) {
        c_key = SecurityHelpers.checkNumeric(request.getParameter("c_key"));
        dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
        data = request.getParameter("date");
        LocalDate localDate = LocalDate.parse(data);
        action_eventi_per_corso(request, response, c_key, dip_key, localDate);
      }
      // TODO Gestire caso senza parametri
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }
}
