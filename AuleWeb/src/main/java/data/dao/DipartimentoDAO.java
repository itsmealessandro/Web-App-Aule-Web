package data.dao;

import java.util.List;

import data.domain.Dipartimento;
import framework.data.DataException;

public interface DipartimentoDAO {

  // Crea e restituisce un nuovo dipartimento
  Dipartimento createDipartimento();

  // Restituisce dipartimento corrente
  Dipartimento getDipartimento(int dipartimento_key) throws DataException;

  // Ritorna una lista di Dipartimenti
  List<Dipartimento> getAllDipartimenti() throws DataException;

  // Salva dipartimento corrente
  void storeAmministratore(Dipartimento dipartimento) throws DataException;

}
