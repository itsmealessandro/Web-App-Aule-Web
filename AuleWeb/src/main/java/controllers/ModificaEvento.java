package controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Corso;
import data.domain.Evento;
import data.domain.Responsabile;
import data.domainImpl.Ricorrenza;
import data.domainImpl.TipologiaEvento;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;

public class ModificaEvento extends AuleWebBaseController {

  private void action_default(HttpServletRequest request, HttpServletResponse response, int e_key)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Evento evento = dataLayer.getEventoDAO().getEventoByID(e_key);
      request.setAttribute("evento", evento);

      res.activate("adminModificaEvento.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_prepare_creation(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, TemplateManagerException {

    try {
      TemplateResult res = new TemplateResult(getServletContext());
      AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

      Evento evento = dataLayer.getEventoDAO().createEvento();
      evento.setKey(0);
      request.setAttribute("evento", evento);

      res.activate("adminModificaEvento.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_create(HttpServletRequest request, HttpServletResponse response, int e_key, Integer IDMaster,
      String nome, Time oraInizio, Time oraFine, String descrizione, String ricorrenza, Date giorno,
      Date dataFineRicorrenza, String tipologiaEvento, String nAula,
      String emailR, String nCorso)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

    try {

      Evento evento = dataLayer.getEventoDAO().createEvento(); // Creazione di un nuovo evento
      if (!ricorrenza.equals("NESSUNA")) {
        evento.setIDMaster(IDMaster);
        evento.setDataFineRicorrenza(dataFineRicorrenza);
        evento.setRicorrenza(Ricorrenza.valueOf(ricorrenza));
      } else {

        evento.setIDMaster(null); // null nel caso di eventi NON ricorrenti
        evento.setRicorrenza(Ricorrenza.NESSUNA);
        evento.setDataFineRicorrenza(null);
      }

      evento.setKey(e_key);
      evento.setNome(nome);
      evento.setOraInizio(oraInizio);
      evento.setOraFine(oraFine);
      evento.setDescrizione(descrizione);
      evento.setData(giorno);
      evento.setTipologiaEvento(TipologiaEvento.valueOf(tipologiaEvento));

      // Recupero del responsabile, aula e corso dall'input dell'utente
      Aula aula = dataLayer.getAulaDAO().getAulaByName(nAula);
      evento.setAula(aula);

      Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabileByEmail(emailR);
      evento.setResponsabile(responsabile);

      Corso corso = dataLayer.getCorsoDAO().getCorsoByNome(nCorso);
      evento.setCorso(corso);

      dataLayer.getEventoDAO().storeEvento(evento);

      res.activate("operazioneEseguita.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_update(HttpServletRequest request, HttpServletResponse response, int e_key, Integer IDMaster,
      String nome, Time oraInizio, Time oraFine,
      String descrizione, String ricorrenza, Date giorno, Date dataFineRicorrenza, String tipologiaEvento, String nAula,
      String emailR, String nCorso)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
    try {

      Evento evento = dataLayer.getEventoDAO().getEventoByID(e_key);

      evento.setIDMaster(IDMaster);
      evento.setNome(nome);
      evento.setOraInizio(oraInizio);
      evento.setOraFine(oraFine);
      evento.setDescrizione(descrizione);
      evento.setRicorrenza(Ricorrenza.valueOf(ricorrenza));
      evento.setData(giorno);
      evento.setDataFineRicorrenza(dataFineRicorrenza);
      evento.setTipologiaEvento(TipologiaEvento.valueOf(tipologiaEvento));

      Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabileByEmail(emailR);
      evento.setResponsabile(responsabile);

      Corso corso = dataLayer.getCorsoDAO().getCorsoByNome(nCorso);
      evento.setCorso(corso);

      Aula aula = dataLayer.getAulaDAO().getAulaByName(nAula);
      evento.setAula(aula);

      dataLayer.getEventoDAO().storeEvento(evento);

      res.activate("operazioneEseguita.ftl.html", request, response);

    } catch (DataException ex) {
      handleError("Data access exception: " + ex.getMessage(), request, response);
    }
  }

  private void action_delete(HttpServletRequest request, HttpServletResponse response, int e_key)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

    try {
      Evento evento = dataLayer.getEventoDAO().getEventoByID(e_key);
      dataLayer.getEventoDAO().deleteEvento(evento);

      res.activate("operazioneEseguita.ftl.html", request, response);
    } catch (DataException e) {
      handleError("Data access exception: " + e.getMessage(), request, response);
    }
  }

  private void action_delete_ricorrenti(HttpServletRequest request, HttpServletResponse response, int e_key)
      throws IOException, ServletException, TemplateManagerException {

    TemplateResult res = new TemplateResult(getServletContext());
    AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

    try {
      Evento evento = dataLayer.getEventoDAO().getEventoByID(e_key);
      dataLayer.getEventoDAO().deleteEventiRicorrenti(evento);

      res.activate("operazioneEseguita.ftl.html", request, response);
    } catch (DataException e) {
      handleError("Data access exception: " + e.getMessage(), request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    int e_key;
    try {
      if (request.getParameter("e_key") == null) {
        throw new DataException("Nessun Evento Selezionato");
      }
      e_key = SecurityHelpers.checkNumeric(request.getParameter("e_key"));
      if (request.getParameter("delete") != null) {
        // Premuto Elimina
        action_delete(request, response, e_key);
      } else if (request.getParameter("deleteRicorrenti") != null) {
        // Premuto Elimina
        action_delete_ricorrenti(request, response, e_key);
      } else if (request.getParameter("IDMaster") != null
          && request.getParameter("oraInizio") != null
          && request.getParameter("oraFine") != null
          && request.getParameter("descrizione") != null
          && request.getParameter("tipologiaEvento") != null
          && request.getParameter("nome") != null
          && request.getParameter("a_name") != null
          && request.getParameter("corso") != null
          && request.getParameter("emailR") != null
          && request.getParameter("ricorrenza") != null
          && request.getParameter("giorno") != null) {

        // Controllo Ricorrenza
        String ricorrenza = request.getParameter("ricorrenza");
        String dataFineRicorrenza = "0";
        if (!ricorrenza.equals("NESSUNA")) {
          dataFineRicorrenza = request.getParameter("dataFineRicorrenza");
        }

        // SE NON RICORRENTE VALORE ZERO
        Date dataFineRicorrenza_sql = null;
        if (!dataFineRicorrenza.equals("0")) {
          dataFineRicorrenza_sql = Date.valueOf(dataFineRicorrenza);
        }
        // SE NON RICORRENTE VALORE ZERO
        Integer IDMaster = SecurityHelpers.checkNumeric(request.getParameter("IDMaster"));
        if (IDMaster == 0) {
          IDMaster = null;
        }

        String oraInizio = request.getParameter("oraInizio");
        String oraFine = request.getParameter("oraFine");
        String giorno = request.getParameter("giorno");
        String tipologiaEvento = request.getParameter("tipologiaEvento");

        // Controllo TipologiaEvento
        boolean trovato = false;
        for (TipologiaEvento t : TipologiaEvento.values()) {
          if (t.toString().equals(tipologiaEvento)) {
            trovato = true;
            break;
          }
        }
        if (!trovato) {
          throw new DataException("Input TipologiaEvento non Valido");
        }

        String descrizione = request.getParameter("descrizione");
        String nome = request.getParameter("nome");
        String aula = request.getParameter("a_name");
        String corso = request.getParameter("corso");
        String responsabile = request.getParameter("emailR");

        // Conversione a sql
        String timeWithSecOI = oraInizio + ":00";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        java.util.Date data1 = sdf.parse(timeWithSecOI);

        String timeWithSecOF = oraFine + ":00";
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        java.util.Date data2 = sdf2.parse(timeWithSecOF);

        Time oraInizio_sql = new Time(data1.getTime());
        Time oraFine_sql = new Time(data2.getTime());
        Date giorno_sql = Date.valueOf(giorno);

        if (e_key != 0) {
          action_update(request, response, e_key, IDMaster, nome, oraInizio_sql, oraFine_sql, descrizione, ricorrenza,
              giorno_sql, dataFineRicorrenza_sql, tipologiaEvento, aula, responsabile, corso);
        } else {
          // Gestisci l'evento singolo
          action_create(request, response, e_key, IDMaster, nome, oraInizio_sql, oraFine_sql, descrizione, ricorrenza,
              giorno_sql, dataFineRicorrenza_sql, tipologiaEvento, aula, responsabile, corso);

        }

      } else if (e_key == 0) {
        // Modalità Creazione
        action_prepare_creation(request, response);
      } else {
        // Mostra Evento
        action_default(request, response, e_key);
      }
    } catch (NumberFormatException | ParseException ex) {
      handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
      handleError(ex, request, response);
    } catch (DataException ex) {
      handleError(ex.getMessage(), request, response);
    }

  }

}
