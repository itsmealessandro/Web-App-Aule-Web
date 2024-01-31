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
import data.domain.Dipartimento;
import data.domain.Responsabile;

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

  private void action_prepare_creation(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Aula aula = dataLayer.getAulaDAO().createAula();
      aula.setKey(0);
      request.setAttribute("aula", aula);

      res.activate("adminModificaAula.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_create(HttpServletRequest request, HttpServletResponse response, int a_key, String nomeDip,
      String nome,
      String luogo, String edificio, String piano, int capienza, int preseElettriche, int preseRete,
      String note, String nomeAttrezzatura, String emailR)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
    try {

      Aula aula = dataLayer.getAulaDAO().createAula();

      aula.setKey(a_key);
      aula.setNome(nome);
      aula.setLuogo(luogo);
      aula.setEdificio(edificio);
      aula.setPiano(piano);
      aula.setCapienza(capienza);
      aula.setPreseElettriche(preseElettriche);
      aula.setPreseRete(preseRete);
      aula.setNote(note);

      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimentoByNome(nomeDip);
      aula.setDipartimento(dipartimento);

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

  private void action_update(HttpServletRequest request, HttpServletResponse response, int a_key, String dip_nome,
      String nome,
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

      Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimentoByNome(dip_nome);
      aula.setDipartimento(dipartimento);

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

  private void action_no_aula() {
    // TODO gestire caso senza aula passata
  }

  private void action_delete(HttpServletRequest request, HttpServletResponse response, int a_key)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

    try {
      Aula aula = dataLayer.getAulaDAO().getAulaByID(a_key);
      dataLayer.getAulaDAO().deleteAula(aula);

      res.activate("operazioneEseguita.ftl.html", request, response);
    } catch (DataException e) {
      handleError("Data access exception: " + e.getMessage(), request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    request.setAttribute("page_title", "Modifica Aula");
    int a_key;
    if (request.getParameter("a_key") == null) {
      action_no_aula();
    }
    a_key = SecurityHelpers.checkNumeric(request.getParameter("a_key"));

    try {
      // Conferma Premuto
      if (request.getParameter("dipartimento") != null
          && request.getParameter("nome") != null
          && request.getParameter("edificio") != null
          && request.getParameter("luogo") != null
          && request.getParameter("piano") != null
          && request.getParameter("capienza") != null
          && request.getParameter("preseElettriche") != null
          && request.getParameter("preseRete") != null
          && request.getParameter("attrezzatura") != null
          && request.getParameter("emailR") != null
          && request.getParameter("note") != null) {

        String dip_nome = request.getParameter("dipartimento");
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

        if (a_key != 0) {
          // Modifica
          action_update(request, response, a_key, dip_nome, nome, luogo, edificio, piano, capienza, preseElettriche,
              preseRete, note, attrezzatura, emailR);
        } else {
          // Creazione
          action_create(request, response, a_key, dip_nome, nome, luogo, edificio, piano, capienza, preseElettriche,
              preseRete, note, attrezzatura, emailR);
        }

      } else if (a_key == 0) {
        // Modalità Creazione
        action_prepare_creation(request, response);
      } else if (request.getParameter("delete") != null) {
        // Premuto Elimina
        action_delete(request, response, a_key);
      } else {
        // Mostra Aula
        action_default(request, response, a_key);
      }

    } catch (NumberFormatException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    }
  }

}
