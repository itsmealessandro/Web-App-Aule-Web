package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Corso;
import data.domain.Evento;
import data.domain.Responsabile;
import framework.data.DataException;
import framework.result.SplitSlashesFmkExt;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityHelpers;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ema
 */
public class GestioneEventi extends AuleWebBaseController {

    // Questo metodo gestisce la richiesta di visualizzare gli eventi per una determinata aula in una data specifica
    private void action_default(HttpServletRequest request, HttpServletResponse response, int IDaula, String data) throws IOException, ServletException, TemplateManagerException {
        try {
            // Ottiene l'oggetto Aula dal database utilizzando l'ID passato come parametro
            Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAulaByID(IDaula);

            // Crea un oggetto TemplateResult per gestire la risposta
            TemplateResult res = new TemplateResult(getServletContext());

            // Imposta gli attributi della richiesta per l'oggetto Aula, la settimana precedente e successiva, e gli eventi nella settimana corrente
            request.setAttribute(("aula"), aula);
            request.setAttribute("settiamanaprecedente", LocalDate.parse(data).plusDays(-7));
            request.setAttribute("settimanasuccessiva", LocalDate.parse(data).plusDays(7));
            request.setAttribute(("eventi"), ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventiBySettimana(aula, Date.valueOf(data)));

            // Attiva il template "gestione_eventi.ftl.html" per generare la risposta
            res.activate("gestione_eventi.ftl.html", request, response);
        } catch (DataException ex) {
            // Gestisce eventuali eccezioni di accesso ai dati e restituisce un messaggio di errore
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    // Questo metodo gestisce la richiesta di visualizzare e modificare un evento
    private void action_write(HttpServletRequest request, HttpServletResponse response, int IDevento, int IDaula) throws IOException, ServletException, TemplateManagerException {
        try {
            // Crea un oggetto TemplateResult per gestire la risposta
            TemplateResult res = new TemplateResult(getServletContext());
            // Aggiunge un oggetto SplitSlashesFmkExt come attributo della richiesta per gestire le barre invertite nelle stringhe
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());

            // Ottiene l'oggetto Aula dal database utilizzando l'ID passato come parametro
            Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAulaByID(IDaula);

            // Ottiene liste di responsabili, corsi e tipologie per utilizzarle nella creazione/modifica dell'evento
            List<Responsabile> responsabili = ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getAllResponsabili();
            List<Corso> corsi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getCorsi();
            //List<Tipologia> tipologie = new ArrayList<>();
            //tipologie.addAll(Arrays.asList(Tipologia.values()));

            // Imposta gli attributi della richiesta con le informazioni ottenute
            request.setAttribute("aula", aula);
            request.setAttribute("responsabili", responsabili);
            request.setAttribute("corsi", corsi);
            //request.setAttribute("tipologie", tipologie);

            if (IDevento > 0) {
                // Se l'IDevento è maggiore di zero, significa che si tratta di una modifica
                // Ottiene l'oggetto Evento dal datalayer in base all'ID per effettuare la modifica
                Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoByID(IDevento);

                if (evento != null) {
                    // Se l'evento esiste, imposta l'attributo della richiesta e attiva il template per la modifica
                    request.setAttribute("evento", evento);
                    res.activate("modifica_evento.ftl.html", request, response);
                } else {
                    // Se l'evento non esiste, gestisce l'errore
                    handleError("Undefined evento", request, response);
                }
            } else {
                // Se l'IDevento è zero, indica la creazione di un nuovo evento
                // Crea un nuovo oggetto Evento utilizzando il datalayer e lo imposta come attributo della richiesta
                Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().createEvento();
                request.setAttribute("evento", evento);
                // Attiva il template per la creazione di un nuovo evento
                res.activate("modifica_evento.ftl.html", request, response);
            }
        } catch (DataException ex) {
            // Gestisce eventuali eccezioni di accesso ai dati e restituisce un messaggio di errore
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

    // Questo metodo gestisce la richiesta di aggiornamento di un evento
    private void action_update(HttpServletRequest request, HttpServletResponse response, int IDevento, int IDaula) throws IOException, ServletException, TemplateManagerException {
        try {
            // Verifica se i parametri richiesti sono presenti e non vuoti
            if (request.getParameter("giorno") != null && request.getParameter("oraInizio") != null
                    && !request.getParameter("nome").isEmpty() && request.getParameter("oraFine") != null
                    && !request.getParameter("descrizione").isEmpty() && request.getParameter("tipologia") != null
                    && (request.getParameter("responsabile") != null || request.getParameter("nomeNuovoResponsabile") != null)) {

                Responsabile responsabile;

                // Se l'utente ha aggiunto i dati di un nuovo Responsabile, lo crea e lo aggiunge alla tabella dei responsabili
                if (!request.getParameter("nomeNuovoResponsabile").isEmpty() && !request.getParameter("emailNuovoResponsabile").isEmpty()) {
                    responsabile = ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().createResponsabile();
                    responsabile.setNome(SecurityHelpers.addSlashes(request.getParameter("nomeNuovoResponsabile")));
                    responsabile.setEmail(SecurityHelpers.addSlashes(request.getParameter("emailNuovoResponsabile")));
                    ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().storeResponsabile(responsabile);
                } else {
                    // Altrimenti, prende quello che è stato scelto dalla select
                    responsabile = ((AuleWebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getResponsabile(SecurityHelpers.checkNumeric(request.getParameter("responsabile")));
                }

                // Ottiene l'oggetto Aula dal database utilizzando l'ID passato come parametro
                Aula aula = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAulaByID(IDaula);

                Corso corso;

                if (SecurityHelpers.addSlashes(request.getParameter("tipologia")).equals("lezione")
                        || SecurityHelpers.addSlashes(request.getParameter("tipologia")).equals("esame")
                        || SecurityHelpers.addSlashes(request.getParameter("tipologia")).equals("parziale")) {
                    if (!request.getParameter("nomeNuovoCorso").isEmpty()) {
                        // Se è presente un nuovo corso, lo crea e lo aggiunge alla tabella dei corsi
                        corso = ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().createCorso();
                        corso.setNome(SecurityHelpers.addSlashes(request.getParameter("nomeNuovoCorso")));
                        ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().storeCorso(corso);
                    } else {
                        // Altrimenti, prende quello che è stato scelto dalla select
                        corso = ((AuleWebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getCorso(SecurityHelpers.checkNumeric(request.getParameter("corso")));
                    }
                } else {
                    corso = null;
                }

                if (request.getParameter("ricorrente") != null) {
                    // L'evento è ricorrente
                    Date giornoInizio = Date.valueOf(SecurityHelpers.addSlashes(request.getParameter("giorno")));

                    if (IDevento > 0) {
                        // Modifica più eventi ricorrenti
                        Evento e = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoByID(IDevento);
                        List<Evento> eventi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventiRicorrenti(e.getNome(), e.getResponsabile().getKey());

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(giornoInizio);

                        long da1 = e.getGiorno().getTime();
                        long da2 = cal.getTimeInMillis();

                        long timeDiff = da2 - da1;
                        int daysDiff = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

                        for (Evento evento : eventi) {
                            cal.add(Calendar.DATE, daysDiff);
                            giornoInizio.setTime(cal.getTimeInMillis());

                            // Aggiorna le informazioni dell'evento
                            updateEventoAttributes(request, responsabile, aula, corso, evento, giornoInizio);

                            // Salva l'evento nel database
                            ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().storeEvento(evento);
                        }

                    } else {
                        // Inserisce più eventi ricorrenti
                        if (!request.getParameter("finericorrenza").isEmpty()) {
                            Date giornoFine = Date.valueOf(SecurityHelpers.addSlashes(request.getParameter("finericorrenza")));
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(giornoInizio);

                            while (giornoInizio.compareTo(giornoFine) < 0) {
                                Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().createEvento();

                                // Aggiorna le informazioni dell'evento
                                updateEventoAttributes(request, responsabile, aula, corso, evento, giornoInizio);

                                // Salva l'evento nel database
                                ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().storeEvento(evento);

                                cal.add(Calendar.DATE, 7);
                                giornoInizio.setTime(cal.getTimeInMillis());
                            }
                        }
                    }

                    // Prepara la risposta per la visualizzazione degli eventi nella gestione delle aule
                    prepareEventsResponse(request, response, aula);

                } else {
                    // È un evento singolo

                    Evento evento;

                    if (IDevento > 0) {
                        // Se l'IDevento è maggiore di zero, significa che si tratta di una modifica
                        evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoByID(IDevento);
                    } else {
                        // Altrimenti, si tratta di un nuovo evento
                        evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().createEvento();
                    }

                    // Aggiorna le informazioni dell'evento
                    updateEventoAttributes(request, responsabile, aula, corso, evento, Date.valueOf(SecurityHelpers.addSlashes(request.getParameter("giorno"))));

                    // Salva l'evento nel database
                    ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().storeEvento(evento);

                    // Delega il resto del processo all'azione write
                    action_write(request, response, evento.getKey(), aula.getKey());
                }
            } else {
                handleError("Cannot update evento: insufficient parameters", request, response);
            }
        } catch (DataException ex) {
            handleError("Data access exception: " + ex.getMessage(), request, response);
        }
    }

// Metodo di utilità per aggiornare gli attributi di un evento
    private void updateEventoAttributes(HttpServletRequest request, Responsabile responsabile, Aula aula, Corso corso, Evento evento, Date giornoInizio) {
        evento.setGiorno(giornoInizio);
        evento.setOraInizio(Time.valueOf(SecurityHelpers.addSlashes(request.getParameter("oraInizio").substring(0, 5)) + ":00"));
        evento.setOraFine(Time.valueOf(SecurityHelpers.addSlashes(request.getParameter("oraFine").substring(0, 5)) + ":00"));
        evento.setNome(SecurityHelpers.addSlashes(request.getParameter("nome")));
        evento.setDescrizione(SecurityHelpers.addSlashes(request.getParameter("descrizione")));
        //evento.setTipologia(Tipologia.valueOf(SecurityHelpers.addSlashes(request.getParameter("tipologia"))));
        evento.setResponsabile(responsabile);
        evento.setAula(aula);

        if (corso != null) {
            evento.setCorso(corso);
        } else {
            evento.setCorso(null);
            evento.removeCorso();
        }
    }

// Metodo di utilità per preparare la risposta per la visualizzazione degli eventi nella gestione delle aule
    
    private void prepareEventsResponse(HttpServletRequest request, HttpServletResponse response, Aula aula) throws TemplateManagerException, IOException, ServletException {
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("aula", aula);
        request.setAttribute("settiamanaprecedente", LocalDate.parse(LocalDate.now().toString()).plusDays(-7));
        request.setAttribute("settimanasuccessiva", LocalDate.parse(LocalDate.now().toString()).plusDays(7));
        request.setAttribute(("eventi"), ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventiBySettimana(aula, Date.valueOf(LocalDate.now().toString())));
        res.activate("gestione_eventi.ftl.html", request, response);
    }
    
    // Questo metodo gestisce la richiesta di eliminazione di un evento
    private void action_delete(HttpServletRequest request, HttpServletResponse response, int IDevento) throws IOException, ServletException, TemplateManagerException {
        try {
            // Ottiene l'oggetto Evento dal datalayer utilizzando l'ID passato come parametro
            Evento evento = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventoByID(IDevento);

            // Elimina l'evento dal database
            ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().deleteEvento(evento);

        } catch (DataException ex) {
            // Gestisce eventuali eccezioni di accesso ai dati
            Logger.getLogger(GestioneEventi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Questo metodo viene chiamato per elaborare la richiesta HTTP
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        int IDaula;
        int IDevento;
        String data;

        try {
            // Verifica se il parametro "data" è presente nella richiesta
            if (request.getParameter("data") == null) {
                // Se manca, imposta la data corrente come default
                data = LocalDate.now().toString();
            } else {
                // Altrimenti, utilizza la data fornita (sanitizzandola)
                data = SecurityHelpers.sanitizeFilename(request.getParameter("data"));
            }

            // Ottiene e verifica l'ID dell'aula dalla richiesta
            IDaula = SecurityHelpers.checkNumeric(request.getParameter("IDaula"));
            request.setAttribute("IDaula", IDaula);

            // Imposta l'attributo della richiesta con la data
            request.setAttribute("data", data);

            // Se viene passato l'ID di un evento da cancellare, chiama action_delete
            if (request.getParameter("delete") != null) {
                IDevento = SecurityHelpers.checkNumeric(request.getParameter("IDevento"));
                action_delete(request, response, IDevento);
                action_default(request, response, IDaula, data);
            } else {
                // Se viene passato l'ID di un evento, controlla se si tratta di un aggiornamento o di un inserimento
                if (request.getParameter("IDevento") != null) {
                    IDevento = SecurityHelpers.checkNumeric(request.getParameter("IDevento"));

                    if (request.getParameter("update") != null) {
                        // Aggiornamento
                        action_update(request, response, IDevento, IDaula);
                    } else {
                        // Inserimento
                        action_write(request, response, IDevento, IDaula);
                    }
                } else {
                    // Altrimenti, effettua l'azione di default
                    action_default(request, response, IDaula, data);
                }
            }

        } catch (NumberFormatException ex) {
            // Gestisce l'eccezione se viene inserito un numero non valido
            handleError("Invalid number submitted", request, response);
        } catch (IOException | TemplateManagerException ex) {
            // Gestisce altre eccezioni
            handleError(ex, request, response);
        }
    }

}
