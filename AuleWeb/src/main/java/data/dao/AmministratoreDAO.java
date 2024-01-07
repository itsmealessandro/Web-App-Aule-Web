package data.dao;

import data.domain.Amministratore;
import framework.data.DataException;

public interface AmministratoreDAO {
    
    // Crea e restituisce un nuovo amministratore
    Amministratore createAmministratore();

    // Restituisce l'amministratore corrente
    Amministratore getAmministratore(int admin_key) throws DataException;
    
    Amministratore getAmministratoreByUsername(String username) throws DataException;

    // Salva l'amministratore corrente
    void storeAmministratore(Amministratore a) throws DataException;
    
}
