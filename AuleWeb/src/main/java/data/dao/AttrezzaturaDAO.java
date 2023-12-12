package data.dao;

import data.domain.Attrezzatura;
import data.domain.Aula;
import framework.data.DataException;
import java.util.List;

public interface AttrezzaturaDAO {
    
    // Crea e restituisce una nuova attrezzatura
    Attrezzatura creaNuovaAttrezzatura()throws DataException;

    // Restituisce l'attrezzatura corrente
    Attrezzatura getAttrezzaturaCorrente(int attrezzatura_key) throws DataException;;

    // Restituisce tutte le attrezzature disponibili
    List<Attrezzatura> getTutteLeAttrezzature()throws DataException;

    // Restituisce tutte le attrezzature associate a un'aula specifica
    List<Attrezzatura> getAttrezzaturePerAula(Aula aula)throws DataException;

    // Salva l'attrezzatura corrente
    void storeAttrezzatura(Attrezzatura attrezzatura)throws DataException;
}
