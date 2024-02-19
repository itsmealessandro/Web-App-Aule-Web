
package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Evento;
import framework.data.DataException;
import framework.result.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EsportaEventoCSV extends AuleWebBaseController {
    
    private void action_expEventoCSV(HttpServletRequest request, HttpServletResponse response, Time oraInizio, Time oraFine) throws IOException {
        try {
            StreamResult result = new StreamResult(getServletContext());

            File in = new File(getServletContext().getRealPath("") + File.separatorChar + "aule.csv");

            BufferedWriter writer = new BufferedWriter(new FileWriter(in));

            String header = "nome;oraInizio;oraFine;descrizione;ricorrenza;data;dataFineRicorrenza;tipologiaEvento;IDResponsabile;IDCorso;IDAula";
            writer.write(header);
            writer.newLine();
            
            String timeWithSecOI = oraInizio + ":00";
                 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                 java.util.Date data1 = sdf.parse(timeWithSecOI);

                 String timeWithSecOF = oraFine + ":00";
                 SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                 java.util.Date data2 = sdf2.parse(timeWithSecOF);

                Time oraInizio_sql = new Time(data1.getTime());
                Time oraFine_sql = new Time(data2.getTime());

            List<Evento> listaEventi= ((AuleWebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventiByOrario(oraInizio_sql,oraFine_sql);

           
            for (Evento a : listaEventi) {

                String dati = a.getNome() + ";" + String.valueOf(a.getOraInizio()) + ";" + String.valueOf(a.getOraFine()) + ";"
                        + a.getDescrizione() + ";" + String.valueOf(a.getRicorrenza()) + ";"
                        + String.valueOf(a.getData()) + ";" + String.valueOf(a.getDataFineRicorrenza())
                        + ";" + String.valueOf(a.getTipologiaEvento())+ ";" + String.valueOf(a.getResponsabile().getKey()) + ";"
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
        } catch (ParseException ex) {
            Logger.getLogger(EsportaEventoCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

