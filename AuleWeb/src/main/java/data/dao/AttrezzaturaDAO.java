
package data.dao;

import data.domain.Attrezzatura;
import data.domain.Aula;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface AttrezzaturaDAO {
    
    // Crea e restituisce una nuova attrezzatura
    Attrezzatura creaNuovaAttrezzatura();

    // Restituisce l'attrezzatura corrente
    Attrezzatura getAttrezzaturaCorrente();

    // Restituisce tutte le attrezzature disponibili
    List<Attrezzatura> getTutteLeAttrezzature();

    // Restituisce tutte le attrezzature associate a un'aula specifica
    List<Attrezzatura> getAttrezzaturePerAula(Aula aula);

    // Salva l'attrezzatura corrente
    void salvaAttrezzaturaCorrente();

    
    void storeAttrezzatura();
}
