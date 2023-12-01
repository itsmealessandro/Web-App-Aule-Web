
package data.dao;

import data.domain.Amministratore;

/**
 *
 * @author emanu
 */
public interface AmministratoreDAO {
    
    // Crea e restituisce un nuovo amministratore
    Amministratore creaNuovoAmministratore();

    // Restituisce l'amministratore corrente
    Amministratore getAmministratoreCorrente();

    // Salva l'amministratore corrente
    void salvaAmministratoreCorrente();

    // Restituisce l'amministratore in base al nome utente
    Amministratore getAmministratoreDaUsername(String username);

    
}
