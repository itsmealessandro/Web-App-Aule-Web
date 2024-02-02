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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

            res.activate("adminModificaAula.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_create(HttpServletRequest request, HttpServletResponse response, int e_key, int IDMaster, String nome, Time oraInizio, Time oraFine, String descrizione, String ricorrenza, String tipologiaEvento, Date giorno, int a_key, int r_key, int c_key) throws IOException, ServletException, TemplateManagerException {

        TemplateResult res = new TemplateResult(getServletContext());
        AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
        try {

            Evento evento = dataLayer.getEventoDAO().createEvento();

            evento.setKey(e_key);
            evento.setOraInizio(oraInizio);
            evento.setOraFine(oraFine);
            evento.setData(giorno);
            evento.setTipologiaEvento(TipologiaEvento.valueOf(tipologiaEvento));
            evento.setDescrizione(descrizione);
            evento.setNome(nome);

            dataLayer.getEventoDAO().storeEvento(evento);

            res.activate("operazioneEseguita.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_update(HttpServletRequest request, HttpServletResponse response, int e_key, int IDMaster, String nome, Time oraInizio, Time oraFine, String descrizione, String ricorrenza, String tipologiaEvento, Date giorno, int a_key, int r_key, int c_key) throws IOException, ServletException, TemplateManagerException {

        TemplateResult res = new TemplateResult(getServletContext());
        AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");
        try {

            Evento evento = dataLayer.getEventoDAO().createEvento();

            evento.setKey(e_key);
            evento.setOraInizio(oraInizio);
            evento.setOraFine(oraFine);
            evento.setData(giorno);
            evento.setTipologiaEvento(TipologiaEvento.valueOf(tipologiaEvento));
            evento.setDescrizione(descrizione);
            evento.setNome(nome);

            

            dataLayer.getEventoDAO().storeEvento(evento);

            res.activate("operazioneEseguita.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    private void action_no_evento() {
        // TODO gestire caso senza aula passata
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

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        int e_key;
        if (request.getParameter("e_key") == null) {
            action_no_evento();
        }
        e_key = SecurityHelpers.checkNumeric(request.getParameter("e_key"));
        try {
            if (request.getParameter("IDMaster") != null
                    && request.getParameter("oraInizio") != null
                    && request.getParameter("oraFine") != null
                    && request.getParameter("descrizione") != null
                    && request.getParameter("tipologiaEvento") != null
                    && request.getParameter("nome") != null
                    && request.getParameter("a_key") != null
                    && request.getParameter("c_key") != null
                    && request.getParameter("r_key") != null
                    && request.getParameter("giorno") != null
                    && request.getParameter("ricorrenza") != null) {

                String oraInizio = request.getParameter("oraInizio");
                String oraFine = request.getParameter("oraFine");
                String giorno = request.getParameter("giorno");
                String tipologiaEvento = request.getParameter("tipologiaEvento");
                String descrizione = request.getParameter("descrizione");
                String nome = request.getParameter("nome");
                String ricorrenza = request.getParameter("ricorrenza");
                int IDMaster = SecurityHelpers.checkNumeric(request.getParameter("IDMaster"));
                int r_key = SecurityHelpers.checkNumeric(request.getParameter("r_key"));
                int c_key = SecurityHelpers.checkNumeric(request.getParameter("c_key"));
                int a_key = SecurityHelpers.checkNumeric(request.getParameter("a_key"));

                Time oraInizio_sql = Time.valueOf(oraInizio);
                Time oraFine_sql = Time.valueOf(oraFine);
                Date giorno_sql = Date.valueOf(giorno);
                if (e_key != 0) {
                    // Modifica
                    action_update(request, response, e_key, IDMaster, nome, oraInizio_sql, oraFine_sql, descrizione, ricorrenza, tipologiaEvento, giorno_sql, a_key, r_key, c_key);
                } else {
                    // Creazione
                    action_create(request, response, e_key, IDMaster, nome, oraInizio_sql, oraFine_sql, descrizione, ricorrenza, tipologiaEvento, giorno_sql, a_key, r_key, c_key);
                }

            } else if (e_key == 0) {
                // Modalità Creazione
                action_prepare_creation(request, response);
            } else if (request.getParameter("delete") != null) {
                // Premuto Elimina
                action_delete(request, response, e_key);
            } else {
                // Mostra Evento
                action_default(request, response, e_key);
            }
        } catch (NumberFormatException ex) {
            handleError("Invalid number submitted", request, response);
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }

    }
}
