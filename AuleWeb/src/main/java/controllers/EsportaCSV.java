package controllers;

import data.dao.AuleWebDataLayer;
import data.domain.Aula;
import data.domain.Dipartimento;
import data.domain.Evento;
import framework.data.DataException;
import framework.result.StreamResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */

public class EsportaCSV extends AuleWebBaseController {

    private void action_expCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            StreamResult result = new StreamResult(getServletContext());

            File in = new File(getServletContext().getRealPath("") + File.separatorChar + "aule.csv");

            BufferedWriter writer = new BufferedWriter(new FileWriter(in));

            String header = "nome;luogo;edificio;piano;capienza;preseElettriche;preseRete;note;IDAttrezzature;IDDipartimento;IDResponsabile";
            writer.write(header);
            writer.newLine();

            List<Aula> listaAule = ((AuleWebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAllAule();

            //scorriamo tutte le aule e scriviamo, una riga alla volta, una aula per riga
            //scorriamo tutte le aule e scriviamo, una riga alla volta, una aula per riga
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
            // handleError(ex, request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
