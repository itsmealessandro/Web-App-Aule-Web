
package dao;

import data.domain.Amministratore;

/**
 *
 * @author emanu
 */
public interface AmministratoreDAO {
    
    // Crea e restituisce un nuovo amministratore
    Amministratore creaNuovoAmministratore();

    // Restituisce l'amministratore corrente
    Amministratore ottieniAmministratoreCorrente();

    // Salva l'amministratore corrente
    void salvaAmministratoreCorrente();

    // Restituisce l'amministratore in base al nome utente
    Amministratore ottieniAmministratoreDaUsername(String username);

    
}
