package data.dao;

import data.domain.Aula;
import data.domain.Dipartimento;
import data.domain.Evento;
import framework.data.DataException;
import java.util.List;

public interface AulaDAO {

  // Crea e restituisce una nuova aula
  Aula createAula() throws DataException;

  // Restituisce aula dato l'ID
  Aula getAulaByID(int aula_key) throws DataException;

  // Restituisce aula dato il nome
  Aula getAulaByName(String aula_name) throws DataException;

  // Restituisce tutte le aule disponibili
  List<Aula> getAllAule() throws DataException;

  // Restituisce tutte le aule associate a un evento specifico
  List<Aula> getAulePerEvento(Evento evento) throws DataException;

  // Restituisce l'aula associata a un dipartimento specifico
  List<Aula> getAulaPerDipartimento(Dipartimento dipartimento) throws DataException;

  void storeAula(Aula aula) throws DataException;

  void deleteAula(Aula aula) throws DataException;
}
