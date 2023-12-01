
package data.dao;

import data.domain.Attrezzatura;
import data.domain.Aula;
import framework.data.DAO;
import framework.data.DataLayer;
import java.util.List;

/**
 *
 * @author emanu
 */
public class AttrezzaturaDAO_Database extends DAO implements AttrezzaturaDAO{

    public AttrezzaturaDAO_Database(DataLayer d) {
        super(d);
    }

    @Override
    public Attrezzatura creaNuovaAttrezzatura() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attrezzatura getAttrezzaturaCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Attrezzatura> getTutteLeAttrezzature() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Attrezzatura> getAttrezzaturePerAula(Aula aula) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void salvaAttrezzaturaCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void storeAttrezzatura() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
