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
import data.domain.Dipartimento;
import data.domain.Evento;

public class EventiTreOre extends AuleWebBaseController {

    private void action_eventi_tre_ore(HttpServletRequest request, HttpServletResponse response, int dipKey)
            throws IOException, ServletException, TemplateManagerException {
        try {
             TemplateResult res = new TemplateResult(getServletContext());
             AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

             Dipartimento dipartimento = dataLayer.getDipartimentoDAO().getDipartimento(dipKey);
             List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByTreOre(dipartimento);

             request.setAttribute("eventi",listaEventi);

             res.activate("eventiTreOre.ftl.html", request, response);

        } catch (DataException e) {
            //TODO da gestire
        }

    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{

           try {
             TemplateResult res = new TemplateResult(getServletContext());
             AuleWebDataLayer dataLayer = (AuleWebDataLayer) request.getAttribute("datalayer");

             List<Dipartimento> listaDipartimenti = dataLayer.getDipartimentoDAO().getAllDipartimenti();
             Dipartimento dipartimento = listaDipartimenti.get(0);
             List<Evento> listaEventi = dataLayer.getEventoDAO().getEventiByTreOre(dipartimento);

             request.setAttribute("eventi",listaEventi);

             res.activate("eventiTreOre.ftl.html", request, response);

        } catch (DataException e) {
            //TODO da gestire
        }
    }


    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("page_title","Eventi Per Ore");

        try {
            if(request.getParameter("d_key")!= null){
                int dipKey = SecurityHelpers.checkNumeric(request.getParameter("d_key"));
                action_eventi_tre_ore(request, response, dipKey);
            }
            else{
                action_default(request,response);
            }

        } catch (NumberFormatException e) {
            //TODO da gestire
        }
        catch (IOException | TemplateManagerException ex) {
            //TODO da gestire
        }
    }
}