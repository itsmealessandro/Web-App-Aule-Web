package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import framework.data.DataException;
import framework.result.StreamResult;

public class EsportaAuleCSV extends AuleWebBaseController {

  private void action_expCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {

    try {

      StreamResult result = new StreamResult(getServletContext());

      File in = new File(getServletContext().getRealPath("") + File.separatorChar + "aule.csv");

      BufferedWriter writer = new BufferedWriter(new FileWriter(in));

      String header = "nome;luogo;edificio;piano;capienza;preseElettriche;preseRete;note;IDAttrezzature;IDDipartimento;IDResponsabile";
      writer.write(header);
      writer.newLine();

      List<Aula> listaAule = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule();

      for (Aula a : listaAule) {

        String dati = a.getNome() + ";" + a.getLuogo() + ";" + a.getEdificio() + ";"
            + a.getPiano() + ";" + String.valueOf(a.getCapienza()) + ";"
            + String.valueOf(a.getPreseElettriche()) + ";" + String.valueOf(a.getPreseRete())
            + ";" + a.getNote() + ";" + String.valueOf(a.getAttrezzatura()) + ";"
            + String.valueOf(a.getDipartimento().getKey()) + ";" + String.valueOf(a.getResponsabile().getKey());

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

      action_expCSV(request, response);

      throw new UnsupportedOperationException("errore");
    } catch (IOException ex) {
      Logger.getLogger(EsportaAuleCSV.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
