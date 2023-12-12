package data.dao;

import data.domain.Dipartimento;
import framework.data.DataException;

public interface DipartimentoDAO {
    
   // Crea e restituisce un nuovo dipartimento
    Dipartimento createDipartimento();

    // Restituisce dipartimento corrente
    Dipartimento getDipartimento(int dipartimento_key) throws DataException;

    // Salva dipartimento corrente
    void storeAmministratore(Dipartimento dipartimento) throws DataException;
    
}
