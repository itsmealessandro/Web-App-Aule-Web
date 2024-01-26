package data.dao;

import java.util.List;

import data.domain.Aula;
import data.domain.Evento;
import framework.data.DataException;

public interface EventoDAO {

  // Crea e restituisce un nuovo evento
  Evento createEvento() throws DataException;

  // Salva l'evento corrente
  void storeEvento(Evento evento) throws DataException;

  // Restituisce l'evento corrente
  Evento getEventoByID(int evento_key) throws DataException;

  List<Evento> getEventiByAula(Aula aula) throws DataException;
  
  // Restituisce tutti gli eventi in una settimana specifica
  List<Evento> getEventiPerSettimana(Aula aula, int numeroSettimana) throws DataException;
  /*
   * // Restituisce tutti gli eventi associati a un corso specifico
   * List<Evento> getEventiPerCorso(Corso corso) throws DataException;
   * 
   * // Restituisce tutti gli eventi gestiti da un responsabile specifico
   * List<Evento> getEventiPerResponsabile(Responsabile responsabile) throws
   * DataException;
   * 
   * // Restituisce tutti gli eventi disponibili
   * List<Evento> getTuttiGliEventi() throws DataException;
   * 
   * 
   * // Restituisce tutti gli eventi ricorrenti
   * List<Evento> getEventiRicorrenti() throws DataException;
   * 
   * // Restituisce tutti gli eventi in una specifica data
   * List<Evento> getEventiPerGiorno(LocalDate data) throws DataException;
   * 
   * // Restituisce tutti gli eventi in una settimana specifica associati a un
   * corso specifico
   * List<Evento> getEventiPerSettimanaECorso(int numeroSettimana, Corso corso)
   * throws DataException;
   * 
   * // Elimina l'evento corrente
   * void eliminaEventoCorrente() throws DataException;
   */
}
