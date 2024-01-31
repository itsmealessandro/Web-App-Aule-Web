package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Corso;
import data.domain.Evento;
import data.domain.Responsabile;
import data.domainImpl.TipologiaEvento;
import framework.data.DataException;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificaEvento extends AuleWebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response, int a_key)
            throws IOException, ServletException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

            Evento evento = dataLayer.getEventoDAO().getEventoByID(a_key);
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

            res.activate("adminModificaAula.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_create(HttpServletRequest request, HttpServletResponse response, int a_key, Time oraInizio, Time oraFine, Date dataInizio, Date dataFine, TipologiaEvento tipologiaEvento, String descrizione, String nome, String emailR) throws IOException, ServletException, TemplateManagerException {

        TemplateResult res = new TemplateResult(getServletContext());
        AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
        try {

            Evento evento = dataLayer.getEventoDAO().createEvento();

            evento.setKey(a_key);
            evento.setDescrizione(descrizione);
            evento.setNome(nome);
            evento.setOraInizio(oraInizio);
            evento.setOraFine(oraFine);
            evento.getRicorrenza();
            evento.setDataInizio(dataInizio);
            evento.setDataFine(dataFine);
            //evento.setCorso(corso);
            evento.setTipologiaEvento(tipologiaEvento);

            Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabileByEmail(emailR);
            evento.setResponsabile(responsabile);

            dataLayer.getEventoDAO().storeEvento(evento);

            res.activate("operazioneEseguita.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_update(HttpServletRequest request, HttpServletResponse response, int a_key,  Time oraInizio, Time oraFine, Date dataInizio, Date dataFine, TipologiaEvento tipologiaEvento, String descrizione, String nome, String emailR) throws IOException, ServletException, TemplateManagerException {

        TemplateResult res = new TemplateResult(getServletContext());
        AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
        try {

            Evento evento = dataLayer.getEventoDAO().createEvento();

            evento.setKey(a_key);
            evento.setDescrizione(descrizione);
            evento.setNome(nome);
            evento.setOraInizio(oraInizio);
            evento.setOraFine(oraFine);
            evento.getRicorrenza();
            evento.setDataInizio(dataInizio);
            evento.setDataFine(dataFine);
            //evento.setCorso(corso);
            evento.setTipologiaEvento(tipologiaEvento);

            Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabileByEmail(emailR);
            evento.setResponsabile(responsabile);

            dataLayer.getEventoDAO().storeEvento(evento);

            res.activate("operazioneEseguita.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }
    
    private void action_no_evento() {
    // TODO gestire caso senza aula passata
  }

    private void action_delete(HttpServletRequest request, HttpServletResponse response, int a_key)
            throws IOException, ServletException, TemplateManagerException {

        TemplateResult res = new TemplateResult(getServletContext());
        AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

        try {
            Evento evento = dataLayer.getEventoDAO().getEventoByID(a_key);
            dataLayer.getEventoDAO().deleteEvento(evento);

            res.activate("operazioneEseguita.ftl.html", request, response);
        } catch (DataException e) {
            handleError("Data access exception: " + e.getMessage(), request, response);
        }
    }
    
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {

    request.setAttribute("page_title", "Modifica Evento");
    int a_key;

    if (request.getParameter("a_key") == null) {
        action_no_evento();
        return;
    }

    a_key = SecurityHelpers.checkNumeric(request.getParameter("a_key"));

    try {
        // Conferma Premuto
        if (request.getParameter("oraInizio") != null
                && request.getParameter("oraFine") != null
                && request.getParameter("dataInizio") != null
                && request.getParameter("dataFine") != null
                //&& request.getParameter("corso") != null
                && request.getParameter("tipologiaEvento") != null
                && request.getParameter("descrizione") != null
                && request.getParameter("nome") != null
                && request.getParameter("emailR") != null) {

            String oraInizioStr = request.getParameter("oraInizio");
            String oraFineStr = request.getParameter("oraFine");
            String dataInizioStr = request.getParameter("dataInizio");
            String dataFineStr = request.getParameter("dataFine");
            //String corsoStr = request.getParameter("corso");
            TipologiaEvento tipologiaEvento = TipologiaEvento.valueOf(request.getParameter("tipologiaEvento"));
            String descrizione = request.getParameter("descrizione");
            String nome = request.getParameter("nome");
            String emailR = request.getParameter("emailR");

            // Converti le stringhe in oggetti Time e Date
            Time oraInizio = Time.valueOf(oraInizioStr);
            Time oraFine = Time.valueOf(oraFineStr);
            Date dataInizio = Date.valueOf(dataInizioStr);
            Date dataFine = Date.valueOf(dataFineStr);
            //Corso corso = Corso.valueOf(corsoStr);

            if (a_key != 0) {
                // Modifica
                action_update(request, response, a_key, oraInizio, oraFine, dataInizio, dataFine, 
                        tipologiaEvento, descrizione, nome, emailR);
            } else {
                // Creazione
                action_create(request, response, a_key, oraInizio, oraFine, dataInizio, dataFine,
                        tipologiaEvento, descrizione, nome, emailR);
            }

        } else if (a_key == 0) {
            // Modalità Creazione
            action_prepare_creation(request, response);
        } else if (request.getParameter("delete") != null) {
            // Premuto Elimina
            action_delete(request, response, a_key);
        } else {
            // Mostra Evento
            action_default(request, response, a_key);
        }

    } catch (NumberFormatException ex) {
        handleError("Invalid number submitted", request, response);
    } catch (IOException | TemplateManagerException ex) {
        handleError(ex, request, response);
    }
}

}
