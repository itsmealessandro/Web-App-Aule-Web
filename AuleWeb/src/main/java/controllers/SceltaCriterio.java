package controllers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Corso;
import data.domain.Dipartimento;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;

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

  private void action_corso(HttpServletRequest request, HttpServletResponse response, int dip_key, String corso_n,
      Date data)
      throws IOException, ServletException, TemplateManagerException {

    try {
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Corso corso = dataLayer.getCorsoDAO().getCorsoByNome(corso_n);
      StringBuffer url = new StringBuffer();

      url.append("EventiPerCorso?");
      url.append("dip_key=" + dip_key);
      url.append("&&");
      url.append("c_key=" + corso.getKey());
      url.append("&&");
      url.append("date=" + data);

      response.sendRedirect(url.toString());
    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_aula(HttpServletRequest request, HttpServletResponse response, int dip_key, String aula_n,
      Date data)
      throws IOException, ServletException, TemplateManagerException {

    try {
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
      Aula aula = dataLayer.getAulaDAO().getAulaByName(aula_n);

      StringBuffer url = new StringBuffer();

      url.append("EventiPerSettimana?");
      url.append("dip_key=" + dip_key);
      url.append("&&");
      url.append("aula_key=" + aula.getKey());
      url.append("&&");
      url.append("date=" + data);

      response.sendRedirect(url.toString());

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_tre_ore(HttpServletRequest request, HttpServletResponse response, int dip_key)
      throws IOException, ServletException, TemplateManagerException {

    StringBuffer url = new StringBuffer();

    url.append("EventiTreOre?");
    url.append("dip_key=" + dip_key);
    response.sendRedirect(url.toString());

  }

  private void action_giorno(HttpServletRequest request, HttpServletResponse response, int dip_key,
      Date data)
      throws IOException, ServletException, TemplateManagerException {

    StringBuffer url = new StringBuffer();

    url.append("EventiPerGiorno?");
    url.append("dip_key=" + dip_key);
    url.append("&&");
    url.append("date=" + data);

    response.sendRedirect(url.toString());
  }

  private void action_esporta(HttpServletRequest request, HttpServletResponse response, int dip_key, Date dataI,
      Date dataF)
      throws IOException, ServletException, TemplateManagerException {

    StringBuffer url = new StringBuffer();

    url.append("EsportaEventiCSV?");
    url.append("dip_key=" + dip_key);
    url.append("&&");
    url.append("dateI=" + dataI);
    url.append("&&");
    url.append("dateF=" + dataF);

    response.sendRedirect(url.toString());
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    request.setAttribute("page_title", "Gestione dipartimenti");

    try {
      int dip_key = 0;
      if (request.getParameter("dip_key") != null) {
        dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
      } else {
        throw new DataException("");
      }

      if (request.getParameter("action") != null) {

        if (request.getParameter("action").equals("confCorso")) { // filtro corso

          String corso_n = request.getParameter("corso_n");
          Date data = Date.valueOf(request.getParameter("data"));

          action_corso(request, response, dip_key, corso_n, data);

        } else if (request.getParameter("action").equals("confAula")) { // filto aula
          String aula_n = request.getParameter("aula_n");
          Date data = Date.valueOf(request.getParameter("data"));
          action_aula(request, response, dip_key, aula_n, data);
        } else if (request.getParameter("action").equals("confTreOre")) {
          action_tre_ore(request, response, dip_key);

        } else if (request.getParameter("action").equals("confGiorno")) {
          Date data = Date.valueOf(request.getParameter("data"));
          action_giorno(request, response, dip_key, data);
        } else if (request.getParameter("action").equals("confIntervallo")) {
          Date dataI = Date.valueOf(request.getParameter("dataI"));
          Date dataF = Date.valueOf(request.getParameter("dataF"));
          action_esporta(request, response, dip_key, dataI, dataF);
        }
      }

      action_default(request, response, dip_key);
    } catch (NumberFormatException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    } catch (DataException ex) {
      handleError("Nessun dipartimento Selezionato", request, response);
    }
  }

}
