package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Evento;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventiPerSettimana extends AuleWebBaseController {

    // Usa l'ID preso dalla GET per caricare la lista di eventi di un'aula 
    private void action_filtro(HttpServletRequest request, HttpServletResponse response, int aula_key, int dip_key, LocalDate date)
            throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

            Aula aula = dataLayer.getAulaDAO().getAulaByID(aula_key);
            List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiSettimanaliByAula(aula, date, dip_key);

            request.setAttribute("aula", aula);
            request.setAttribute("eventi", listaEventi);
            res.activate("eventiPerSettimana.ftl.html", request, response);

        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        // Imposta l'attributo "page_title" della richiesta
        request.setAttribute("page_title", "Gestione Eventi");

        try {
            // Dichiarazione delle variabili
            int aula_key;
            int dip_key;
            String data;

            // Controlla se i parametri "d" e "a" sono presenti nella richiesta
            if (request.getParameter("date") != null && request.getParameter("dip_key") != null && request.getParameter("aula_key") != null) {
                // Recupera il valore del parametro "date" e lo converte in LocalDate
                data = request.getParameter("date");
                LocalDate localDate = LocalDate.parse(data);

                // Recupera i valori dei parametri "dip_key" e "aula_key" e li converte in numeri interi
                dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
                aula_key = SecurityHelpers.checkNumeric(request.getParameter("aula_key"));

                // Esegue l'azione specifica per il filtro con i parametri ottenuti
                action_filtro(request, response, aula_key, dip_key, localDate);
            } else {
                // Se mancano i parametri "dip_key" o "aula_key" o "date", esegui un'azione predefinita
                LocalDate localDate = LocalDate.now();
                dip_key = 1;
                aula_key = 1;
                action_filtro(request, response, aula_key, dip_key, localDate);
            }
        } catch (NumberFormatException ex) {
            // Gestisce l'eccezione se viene inserito un numero non valido
            handleError("Invalid number submitted", request, response);
        } catch (IOException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

}
