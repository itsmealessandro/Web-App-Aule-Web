
package data.dao;

import data.domain.Amministratore;
import framework.data.DAO;
import framework.data.DataLayer;

/**
 *
 * @author emanu
 */
public class AmministratoreDAO_Database extends DAO implements AmministratoreDAO{

    public AmministratoreDAO_Database(DataLayer d) {
        super(d);
    }

    @Override
    public Amministratore creaNuovoAmministratore() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Amministratore getAmministratoreCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void salvaAmministratoreCorrente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Amministratore getAmministratoreDaUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
