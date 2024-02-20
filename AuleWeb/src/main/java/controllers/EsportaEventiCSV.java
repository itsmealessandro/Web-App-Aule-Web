package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.dao.AuleWebDataLayer;
import data.domain.Evento;
import framework.data.DataException;
import framework.result.StreamResult;
import framework.security.SecurityHelpers;

public class EsportaEventiCSV extends AuleWebBaseController {

  private void action_expEventiCSV(HttpServletRequest request, HttpServletResponse response, Date dataI,
      Date dataF, int dip_key) throws IOException {
    try {
      StreamResult result = new StreamResult(getServletContext());

      File in = new File(getServletContext().getRealPath("") + File.separatorChar + "eventi.csv");

      BufferedWriter writer = new BufferedWriter(new FileWriter(in));

      String header = "nome;oraInizio;oraFine;descrizione;ricorrenza;data;dataFineRicorrenza;tipologiaEvento;IDResponsabile;IDCorso;IDAula";
      writer.write(header);
      writer.newLine();

      List<Evento> listaEventi = ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO()
          .getEventiByDate(dip_key, dataI, dataF);

      for (Evento a : listaEventi) {

        String dati = a.getNome() + ";" + String.valueOf(a.getOraInizio()) + ";" + String.valueOf(a.getOraFine()) + ";"
            + a.getDescrizione() + ";" + String.valueOf(a.getRicorrenza()) + ";"
            + a.getData().toString() + ";" + a.getDataFineRicorrenza().toString()
            + ";" + String.valueOf(a.getTipologiaEvento()) + ";" + String.valueOf(a.getResponsabile().getKey()) + ";"
            + String.valueOf(a.getCorso().getKey()) + ";" + String.valueOf(a.getAula().getKey());

        writer.write(dati);

        writer.newLine();
      }

      writer.flush();
      writer.close();
      result.setResource(in);
      result.activate(request, response);
    } catch (DataException ex) {
      handleError(ex, request, response);
    }
  }

  @Override
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    try {
      int dip_key = 0;
      if (request.getParameter("dip_key") != null) {
        dip_key = SecurityHelpers.checkNumeric(request.getParameter("dip_key"));
      } else {
        throw new DataException("Nessun Dipartimento");
      }

      if (request.getParameter("dateI") != null &&
          request.getParameter("dateF") != null) {

        Date dataI = Date.valueOf(request.getParameter("dateI"));
        Date dataF = Date.valueOf(request.getParameter("dateF"));

        action_expEventiCSV(request, response, dataI, dataF, dip_key);
      }

    } catch (DataException ex) {
      handleError(ex, request, response);
    } catch (IOException ex) {
      handleError(ex, request, response);
    }
  }
}
