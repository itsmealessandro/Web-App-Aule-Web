/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.domain.Attrezzatura;
import data.domain.Aula;
import data.domain.Dipartimento;
import data.domain.Evento;
import data.proxy.AulaProxy;
import framework.data.DAO;
import framework.data.DataException;
import framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emanu
 */
public class AulaDAO_Database extends DAO implements AulaDAO{

    private PreparedStatement sAulaPerID;
    private PreparedStatement sAule, sAulePerDipartimento;
    private PreparedStatement iAula, uAula, dAula;
    
    public AulaDAO_Database(DataLayer d) {
        super(d);
    }
    
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            //precompile all the queries uses in this class
            sAulaPerID = connection.prepareStatement("SELECT * FROM Aula WHERE ID=?");
            sAule = connection.prepareStatement("SELECT ID AS aulaID FROM Aula");
            sAulePerDipartimento = connection.prepareStatement("SELECT ID AS aulaID FROM Aula WHERE gruppoID=?");

            //notare l'ultimo paametro extra di questa chiamata a
            //prepareStatement: lo usiamo per assicurarci che il JDBC
            //restituisca la chiave generata automaticamente per il
            //record inserito
            //note the last parameter in this call to prepareStatement:
            //it is used to ensure that the JDBC will sotre and return
            //the auto generated key for the inserted recors
            iAula = connection.prepareStatement("INSERT INTO aula (nome,capienza,emailResponsabile,note,numeroPreseElettriche,numeroPreseRete,gruppoID,posizioneID) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uAula = connection.prepareStatement("UPDATE aula SET nome=?,capienza=?,emailResponsabile=?, note=?, numeroPreseElettriche=?, numeroPreseRete=?, gruppoID=?, posizioneID=?, version=? WHERE ID=? and version=?");
            dAula = connection.prepareStatement("DELETE FROM aula WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing auleweb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {

            sAulaPerID.close();

            sAule.close();
            sAulePerDipartimento.close();
            

            iAula.close();
            uAula.close();
            dAula.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    
    
    @Override
    public Aula createAula() {
        return new AulaProxy(getDataLayer());
    }
    
    public Aula creaNuovaAula(ResultSet rs) throws DataException {
        AulaProxy a = (AulaProxy) createAula();
        try {
            a.setKey(rs.getInt("ID"));
            a.setDipartimentoKey(rs.getInt("IDdipartimento"));
            a.setAttrezzaturaKey(rs.getInt("IDattrezzatura"));
            a.setNome(rs.getString("nome"));
            a.setLuogo(rs.getString("luogo"));
            a.setEdificio(rs.getString("edificio"));
            a.setPiano(rs.getString("piano"));
            a.setCapienza(rs.getInt("capienza"));
            a.setPreseElettriche(rs.getInt("preseElettriche"));
            a.setPreseRete(rs.getInt("preseRete"));
            a.setNote(rs.getString("note"));
            // Non impostiamo il campo version in quanto non presente in AulaProxy
        } catch (SQLException ex) {
            throw new DataException("Unable to create aula object from ResultSet", ex);
        }
        return a;
    }

    @Override
    public Aula getAula(int aula_key) throws DataException {
    Aula a = null;
    // prima vediamo se l'oggetto è già stato caricato
    // first look for this object in the cache
    if (dataLayer.getCache().has(Aula.class, aula_key)) {
        a = dataLayer.getCache().get(Aula.class, aula_key);
    } else {
        // altrimenti lo carichiamo dal database
        // otherwise load it from the database
        try {
            sAulaPerID.setInt(1, aula_key);
            try (ResultSet rs = sAulaPerID.executeQuery()) {
                if (rs.next()) {
                    // Creiamo un'istanza di AulaProxy invece di Aula
                    a = creaNuovaAula(rs);
                    // e lo mettiamo anche nella cache
                    // and put it also in the cache
                    dataLayer.getCache().add(Aula.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aula by ID", ex);
        }
    }
    return a;
}

    @Override
    public List<Aula> getTutteLeAule() throws DataException {
        List<Aula> result = new ArrayList();

        try ( ResultSet rs = sAule.executeQuery()) {
            while (rs.next()) {
                result.add((Aula) getAula(rs.getInt("aulaID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule", ex);
        }
        return result;
    }

    @Override
    public List<Aula> getAulePerAttrezzatura(Attrezzatura attrezzatura) throws DataException {
        List<Aula> result = new ArrayList();

        try {
            sAulePerDipartimento.setInt(1, attrezzatura.getKey());
            try ( ResultSet rs = sAulePerDipartimento.executeQuery()) {
                while (rs.next()) {
                    //la query  estrae solo gli ID degli articoli selezionati
                    //poi sarà getArticle che, con le relative query, popolerà
                    //gli oggetti corrispondenti. Meno efficiente, ma così la
                    //logica di creazione degli articoli è meglio incapsulata
                    //the query extracts only the IDs of the selected articles 
                    //then getArticle, with its queries, will populate the 
                    //corresponding objects. Less efficient, but in this way
                    //article creation logic is better encapsulated
                    result.add((Aula) getAula(rs.getInt("attrezzaturaID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule by attrezzatura", ex);
        }
        return result;
    }
    
    @Override
    public List<Aula> getAulePerEvento(Evento evento) throws DataException {
        List<Aula> result = new ArrayList();

        try {
            sAulePerDipartimento.setInt(1, evento.getKey());
            try ( ResultSet rs = sAulePerDipartimento.executeQuery()) {
                while (rs.next()) {
                    //la query  estrae solo gli ID degli articoli selezionati
                    //poi sarà getArticle che, con le relative query, popolerà
                    //gli oggetti corrispondenti. Meno efficiente, ma così la
                    //logica di creazione degli articoli è meglio incapsulata
                    //the query extracts only the IDs of the selected articles 
                    //then getArticle, with its queries, will populate the 
                    //corresponding objects. Less efficient, but in this way
                    //article creation logic is better encapsulated
                    result.add((Aula) getAula(rs.getInt("eventoID")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule by evento", ex);
        }
        return result;
    }
    

   

    @Override
    public Dipartimento creaNuovoDipartimento() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Dipartimento getDipartimentoCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Dipartimento> getTuttiIDipartimenti() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Aula getAulaPerDipartimento(Dipartimento dipartimento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void salvaDipartimentoCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminaDipartimentoCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
