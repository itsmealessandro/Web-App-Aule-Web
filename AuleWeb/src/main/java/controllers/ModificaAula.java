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
import data.domain.Attrezzatura;
import data.domain.Aula;
import data.domain.Responsabile;

public class ModificaAula extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response, int a_key)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().getAulaByID(a_key);
      if(aula.getResponsabile()== null) System.out.println("no Resp");
      request.setAttribute("aula", aula);

      res.activate("adminModificaAula.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_update(HttpServletRequest request, HttpServletResponse response, int a_key, String nome,
      String luogo, String edificio, String piano, int capienza, int preseElettriche, int preseRete,
      String note, String nomeAttrezzatura, String emailR)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
      Aula aula = dataLayer.getAulaDAO().getAulaByID(a_key);

      aula.setNome(nome);
      aula.setLuogo(luogo);
      aula.setEdificio(edificio);
      aula.setPiano(piano);
      aula.setCapienza(capienza);
      aula.setPreseElettriche(preseElettriche);
      aula.setPreseRete(preseRete);
      aula.setNote(note);

      Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabileByEmail(emailR);
      aula.setResponsabile(responsabile);

      Attrezzatura attrezzatura = dataLayer.getAttrezzaturaDAO().getAttrezzaturaByName(nomeAttrezzatura);
      aula.setAttrezzatura(attrezzatura);

      dataLayer.getAulaDAO().storeAula(aula);

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
      if (request.getParameter("a_key") != null &&
          request.getParameter("nome") != null &&
          request.getParameter("edificio") != null &&
          request.getParameter("luogo") != null &&
          request.getParameter("piano") != null &&
          request.getParameter("capienza") != null &&
          request.getParameter("preseElettriche") != null &&
          request.getParameter("preseRete") != null &&
          request.getParameter("attrezzatura") != null &&
          request.getParameter("emailR") != null &&
          request.getParameter("note") != null) {

        int a_key = SecurityHelpers.checkNumeric(request.getParameter("a_key"));
        String nome = request.getParameter("nome");
        String luogo = request.getParameter("luogo");
        String edificio = request.getParameter("edificio");
        String piano = request.getParameter("piano");
        int capienza = SecurityHelpers.checkNumeric(request.getParameter("capienza"));
        int preseElettriche = SecurityHelpers.checkNumeric(request.getParameter("preseElettriche"));
        int preseRete = SecurityHelpers.checkNumeric(request.getParameter("preseRete"));
        String note = request.getParameter("note");
        String attrezzatura = request.getParameter("attrezzatura");
        String emailR = request.getParameter("emailR");
        action_update(request, response, a_key, nome, luogo, edificio, piano, capienza, preseElettriche,
            preseRete, note, attrezzatura, emailR);

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
