
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
import data.domain.Aula;
import data.domain.Dipartimento;
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

  // Prende i parametri dalla Get e chiama i metodi corrispettivi
  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    request.setAttribute("page_title", "Eventi Per Giorno");
    try {
      SimpleDateFormat sdf = new SimpleDateFormat();
      if (request.getParameter("date") != null) {
        // TODO prendere il paramentro e convertirlo in DATE
        java.util.Date dateJava = sdf.parse(request.getParameter("date"));
        java.sql.Date sqlDate = new java.sql.Date(dateJava.getTime());
        action_eventi_per_giorno(request, response, sqlDate);
      } else {
        // TODO Prendere il giorno odierno e convertirlo in DATE
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
