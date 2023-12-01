
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
    Attrezzatura ottieniAttrezzaturaCorrente();

    // Restituisce tutte le attrezzature disponibili
    List<Attrezzatura> ottieniTutteLeAttrezzature();

    // Restituisce tutte le attrezzature associate a un'aula specifica
    List<Attrezzatura> ottieniAttrezzaturePerAula(Aula aula);

    // Salva l'attrezzatura corrente
    void salvaAttrezzaturaCorrente();

    
    void storeAttrezzatura();
}
