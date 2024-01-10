
package controllers;

import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.AuleWebDataLayer;
import data.domain.Evento;

public class EventiPerGiorno extends AuleWebBaseController {

  // Usa il paramentro date preso dalla GET per lanciare una vista con gli eventi
  // di quella settimana
  private void action_eventi_per_giorno(HttpServletRequest request, HttpServletResponse response, Date data)
      throws IOException, ServletException, TemplateManagerException {
    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByDay(data);

      request.setAttribute("eventi", listaEventi);
      res.activate("eventiGiorno.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  // Prende il parametro dalla Get e lo trasforma in un formato adatto
  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    request.setAttribute("page_title", "Eventi Per Giorno");
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      if (request.getParameter("date") != null) {
        java.util.Date dateJava = sdf.parse(request.getParameter("date"));
        java.sql.Date sqlDate = new java.sql.Date(dateJava.getTime());
        action_eventi_per_giorno(request, response, sqlDate);
      } else {
        LocalDate localDate = LocalDate.now();
        java.sql.Date sqlDate = Date.valueOf(localDate);
        action_eventi_per_giorno(request, response, sqlDate);
      }
    } catch (ParseException pe) {
      // TODO Gestire l'eccezione
      throw new ServletException("bad date format", pe);

    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }
}
